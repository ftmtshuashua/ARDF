package com.lfp.ardf.module.net;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.framework.I.INetRequest;
import com.lfp.ardf.framework.util.SimpleLifeCycleObserve;
import com.lfp.ardf.module.net.i.IChainRequest;
import com.lfp.ardf.module.net.i.IChainRequestHelper;
import com.lfp.ardf.module.net.i.IChainResponseObserver;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 链式请求管理
 * Created by LiFuPing on 2018/5/15.
 */
public class ChainReqeustManager<R extends IChainRequest, C extends IChainResponseObserver<R>> {
    IChainRequestHelper mRequestHelper;
    /*是否启用日志*/
    boolean isDebug = true;


    public ChainReqeustManager() {
        this(new OkHttpRequestHelper());
    }

    public ChainReqeustManager(IChainRequestHelper helper) {
        mRequestHelper = helper;
    }

    /**
     * 绑定框架
     */
    public void setAppFramework(INetRequest appfk) {
        appfk.registeredObserve(mContentObserve);
    }

    /**
     * 获得默认配置
     *
     * @param appfk APP框架
     * @return 请求链管理
     */
    public static ChainReqeustManager defualt(INetRequest appfk) {
        ChainReqeustManager manger = new ChainReqeustManager();
        manger.setAppFramework(appfk);
        return manger;
    }

    /**
     * @return 是否启用日志
     */
    public boolean isDebug() {
        return isDebug;
    }

    /**
     * 启用日志
     *
     * @param debug true;启用
     */
    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    /**
     * 发起请求
     *
     * @param observer      请求回复接受者
     * @param request_chain 请求链
     */
    public void request(C observer, R... request_chain) {
        if (request_chain == null || request_chain.length == 0) return;
        List<R> list = new ArrayList<>();
        for (int i = 0; i < request_chain.length; i++) {
            R request = request_chain[i];
            if (request != null) {
                request.setId(i);
                list.add(request);
            }
        }
        request(observer, list);
    }

    /**
     * 发起请求
     *
     * @param observer      请求回复接受者
     * @param request_chain 请求链
     */
    public void request(C observer, Iterable<R> request_chain) {
        if (request_chain == null) return;
        Iterator<R> iterator = request_chain.iterator();
        List<R> array = new ArrayList<>();
        while (iterator.hasNext()) {
            array.add(iterator.next());
        }
        if (array.isEmpty()) return;

        for (int i = array.size() - 1; i >= 0; i--) {
            R request = array.get(i);
            if (request == null) array.remove(i);
            else request.setId(i);
        }

        prepare(array);

        if (observer.isDisableConcurrentRequest()) {
            initiate(observer, array.get(0));
        } else initiate(observer, array.get(0));

    }

    SimpleLifeCycleObserve mContentObserve = new SimpleLifeCycleObserve() {
        @Override
        public void onDestroy() {
            super.onDestroy();

        }
    };

    /*准备工作，对数据做一些必要的数据处理*/
    void prepare(List<R> array) {
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

    /*发起请求*/
    <R extends IChainRequest> void initiate(final C observer, final R request) {
        LogUtil.e("------------ initiate  a  request ------------");
        if (request == null) {
            observer.onChainComplete();
            return;
        }
        if (!request.hasPre()) observer.onStart();


        Observable.create(new ObservableOnSubscribe<R>() {
            @Override
            public void subscribe(ObservableEmitter<R> emitter) throws Exception {
                if (isDebug)
                    LogUtil.w(MessageFormat.format("↓↓ Request ↓↓\n{0}", request.toString()));
                Object o = mRequestHelper.perform(request);
                emitter.onNext(request);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap(new Function<R, ObservableSource<R>>() {
                    @Override
                    public ObservableSource<R> apply(final R r) throws Exception {
                        LogUtil.e("在这里 - 处理请求耗时操作!");
                        observer.onDataProcessing(request.getId(), request);

                        return Observable.create(new ObservableOnSubscribe<R>() {
                            @Override
                            public void subscribe(ObservableEmitter<R> emitter) throws Exception {
                                emitter.onNext(r);
                                emitter.onComplete();
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<R>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.e("Observer -----> onSubscribe");

                    }

                    @Override
                    public void onNext(R t) {
                        LogUtil.e("Observer -----> onNext");
                        observer.onResponse(request.getId(), request);
                        initiate(observer, request.getNext());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("Observer -----> onError");
                        observer.onError(e);
                        observer.onChainComplete();
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.e("Observer -----> onComplete");
                    }
                });
    }


    /*并发请求*/
    void synInitiate(final C observer, final R request) {

    }

}
