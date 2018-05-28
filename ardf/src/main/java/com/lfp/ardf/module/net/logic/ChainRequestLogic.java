package com.lfp.ardf.module.net.logic;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.module.net.client.IRequestClient;
import com.lfp.ardf.module.net.observer.IRequestObserver;
import com.lfp.ardf.module.net.request.IChainRequest;
import com.lfp.ardf.module.net.request.IRequest;
import com.lfp.ardf.module.net.util.ExceptionTotalUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 将请求列表拉伸为一个链条顺序执行,前一个请求可以控制后面请求的逻辑 <br/>
 * 请在一些特殊情况下使用此模式.例如:登陆完成之后通过登陆信息查询<br/>
 * Created by LiFuPing on 2018/5/29.
 */
public class ChainRequestLogic<R extends IChainRequest> extends ImpRequestLogi<R> {
    CompositeDisposable mCompositeSubscription;
    /**
     * 缓存请求列表
     */
    ArrayList<IRequest> mRequestCache = new ArrayList<>();

    public ChainRequestLogic() {
        this.mCompositeSubscription = new CompositeDisposable();
    }

    /*准备工作，对数据做一些必要的数据处理,将请求列表拉伸为一条请求链*/
    void prepareChain(List<R> array) {
        int size = array.size();
        for (int i = 1; i < size; i++) {
            R request = array.get(i);
            R pre = array.get(i - 1);
            R next = (i + 1) == size ? null : array.get(i + 1);

            pre.setNext(request);
            request.setPre(pre);
            request.setNext(next);
            if (next != null) next.setPre(request);
        }
    }

    @Override
    protected void perform(final IRequestClient<R> client, final IRequestObserver<R> observer, List<R> request_array) {
        prepareChain(request_array);

        List<Observable<R>> observable_array = new ArrayList<>();
        for (final R request : request_array) {
            observable_array.add(Observable.create(new ObservableOnSubscribe<R>() {
                        @Override
                        public void subscribe(ObservableEmitter<R> emitter) throws Exception {
                            mRequestCache.add(request);
                            try {
                                if (LogUtil.isDebug()) request.showRequestLog();
                                client.perform(request);
                                if (LogUtil.isDebug()) request.showResponseLog();

                                observer.onComputation(request);
                                emitter.onNext(request);
                            } catch (Exception e) {
                                if (emitter.isDisposed()) return;
                                if (LogUtil.isDebug())
                                    LogUtil.e(MessageFormat.format("RequestId:{0} - {1}", request.getId(), ExceptionTotalUtil.getThrowableToastInfo(e)));

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

        Observable.concat(observable_array)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<R>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.e(MessageFormat.format("---------------->>>  {0}", "onSubscribe"));
                        mCompositeSubscription.add(d);
                        disposable = d;
                        observer.onStart();
                    }

                    @Override
                    public void onNext(R r) {
                        LogUtil.e(MessageFormat.format("---------------->>>  {0}  ID：{1}", "onNext", r.getId()));
                        observer.onResponse(r);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(MessageFormat.format("---------------->>>  {0}", "onError"));
                        mCompositeSubscription.remove(disposable);
                        observer.onEnd();
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.e(MessageFormat.format("---------------->>>  {0}", "onComplete"));
                        mCompositeSubscription.remove(disposable);
                        observer.onEnd();
                    }
                });

    }

    @Override
    public void shutdown() {
        for (IRequest request : mRequestCache) request.cancel();
        mRequestCache.clear();
        mCompositeSubscription.clear();
    }


}
