package com.lfp.ardf.module.net.logic;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.module.net.client.IRequestClient;
import com.lfp.ardf.module.net.observer.IRequestObserver;
import com.lfp.ardf.module.net.request.IChainRequest;
import com.lfp.ardf.module.net.request.IRequest;

import java.text.MessageFormat;
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
 * 将请求列表拉伸为一个链条顺序执行,前一个请求可以控制后面请求的逻辑 <br/>
 * 请在一些特殊情况下使用此模式.例如:登陆完成之后通过登陆信息查询<br/>
 * Created by LiFuPing on 2018/5/29.
 */
public class ChainRequestLogic<R extends IChainRequest> extends ImpRequestLogi<R> {
    CompositeDisposable mCompositeSubscription;
    /**
     * 缓存请求列表
     */
    Vector<IRequest> mRequestCache = new Vector<>();

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
        if (request_array == null || request_array.isEmpty()) return;
        prepareChain(request_array);
        R first_request = request_array.get(0);
        observer.onStart();
        perform_chain(client, observer, first_request);
    }

    private void perform_chain(final IRequestClient<R> client, final IRequestObserver<R> observer, final R request) {
        if (request == null) {
            observer.onEnd();
            return;
        }
        Observable.create(new ObservableOnSubscribe<R>() {
            @Override
            public void subscribe(ObservableEmitter<R> emitter) throws Exception {
                mRequestCache.add(request);
                try {
                    request.showRequestLog();
                    client.perform(request);
                    request.showResponseLog();
                    if (!emitter.isDisposed()) observer.onComputation(request);
                    if (!emitter.isDisposed()) {
                        emitter.onNext(request);
                        emitter.onComplete();
                    }
                } catch (Exception e) {
                    if (emitter.isDisposed()) return;
                    emitter.onError(e);
                    LogUtil.e(e, MessageFormat.format("Request_Id:{0} - 出错了!", request.getId()));
                } finally {
                    mRequestCache.remove(request);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<R>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeSubscription.add(d);
                        disposable = d;
                    }

                    @Override
                    public void onNext(R r) {
                        observer.onResponse(r);
                        perform_chain(client, observer, (R) r.getNext());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mCompositeSubscription.remove(disposable);
                        observer.onError(request, e);
                        observer.onEnd();
                    }

                    @Override
                    public void onComplete() {
                        mCompositeSubscription.remove(disposable);
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
