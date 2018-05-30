package com.lfp.ardf.module.net.logic;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.module.net.client.IRequestClient;
import com.lfp.ardf.module.net.observer.IRequestObserver;
import com.lfp.ardf.module.net.request.IRequest;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 并发请求，所有请求并发进行。优化请求速度<br/>
 * Created by LiFuPing on 2018/5/28.
 */
public class ParallelRequestLogic<R extends IRequest> extends ImpRequestLogi<R> {
    CompositeDisposable mCompositeSubscription;
    /**
     * 缓存请求列表
     */
    Vector<IRequest> mRequestCache = new Vector<>();

    public ParallelRequestLogic() {
        this.mCompositeSubscription = new CompositeDisposable();
    }

    @Override
    protected synchronized void perform(final IRequestClient<R> client, final IRequestObserver<R> observer, List<R> request_array) {
        if (request_array == null || request_array.isEmpty()) return;
        List<Observable<R>> observable_array = new ArrayList<>();
        for (final R request : request_array) {
            observable_array.add(Observable.create(new ObservableOnSubscribe<R>() {
                        @Override
                        public void subscribe(ObservableEmitter<R> emitter) throws Exception {
                            mRequestCache.add(request);
                            try {
                                request.showRequestLog();
                                client.perform(request);
                                request.showResponseLog();

                                observer.onComputation(request);
                                emitter.onNext(request);
                            } catch (Exception e) {
                                if (emitter.isDisposed()) return;
                                if (LogUtil.isDebug())
                                    LogUtil.e(e, MessageFormat.format("Request_Id:{0} - 出错了!", request.getId()));

                                Observable.just(e)
                                        .subscribeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<Exception>() {
                                            Disposable disposable;

                                            @Override
                                            public void onSubscribe(Disposable d) {
                                                disposable = d;
                                                mCompositeSubscription.add(d);
                                            }

                                            @Override
                                            public void onNext(Exception e) {
                                                observer.onError(request, e);
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                mCompositeSubscription.remove(disposable);
                                            }

                                            @Override
                                            public void onComplete() {
                                                mCompositeSubscription.remove(disposable);
                                            }
                                        });
                            } finally {
                                mRequestCache.remove(request);
                                if (!emitter.isDisposed()) emitter.onComplete();
                            }
                        }
                    })
                            .subscribeOn(Schedulers.io())
            );
        }

        Observable.mergeDelayError(observable_array, getMaxConcurrency())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<R>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeSubscription.add(d);
                        disposable = d;
                        observer.onStart();
                    }

                    @Override
                    public void onNext(R r) {
                        observer.onResponse(r);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mCompositeSubscription.remove(disposable);
                        observer.onEnd();
                    }

                    @Override
                    public void onComplete() {
                        mCompositeSubscription.remove(disposable);
                        observer.onEnd();
                    }
                });
    }

    @Override
    public synchronized void shutdown() {
        for (IRequest request : mRequestCache) request.cancel();
        mRequestCache.clear();
        mCompositeSubscription.clear();
    }
}
