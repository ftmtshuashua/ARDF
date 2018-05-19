package com.lfp.ardf.module.net;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.framework.I.ILifeCycleObserved;
import com.lfp.ardf.framework.I.INetRequest;
import com.lfp.ardf.framework.util.SimpleLifeCycleObserve;
import com.lfp.ardf.module.net.i.IChainRequest;
import com.lfp.ardf.module.net.i.IChainRequestHelper;
import com.lfp.ardf.module.net.i.IChainResponseObserver;
import com.lfp.ardf.module.net.i.IChainResponseObserver.ReqeustModel;
import com.lfp.ardf.util.CpuUtile;

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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 链式请求管理
 * Created by LiFuPing on 2018/5/15.
 */
public class ChainReqeustManager<R extends IChainRequest, C extends IChainResponseObserver<R>> {
    CompositeDisposable mCompositeSubscription;
    IChainRequestHelper mRequestHelper;
    /*是否启用日志*/
    boolean isDebug = true;

    /*允许同时发起的请求数*/
    static int maxConcurrency;

    static {
        try {
            maxConcurrency = (int) (CpuUtile.getNumCores() / 0.3f);
        } catch (Exception e) {
            e.printStackTrace();
            maxConcurrency = 20;
        }
    }

    public ChainReqeustManager() {
        this(new OkHttpRequestHelper());
        mCompositeSubscription = new CompositeDisposable();
    }

    public ChainReqeustManager(IChainRequestHelper helper) {
        mRequestHelper = helper;
    }

    /**
     * 绑定框架 ,以便在必要的时候对请求做处理，例如在Activity关闭的时候中断请求
     *
     * @param appfk 框架
     */
    public void setAppFramework(ILifeCycleObserved appfk) {
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
     * 取消所有请求
     */
    public void cancelRequest() {
        mRequestHelper.cancel(null);
    }

    /**
     * 在Activity关闭的时候应该结束所有请求
     */
    SimpleLifeCycleObserve mContentObserve = new SimpleLifeCycleObserve() {
        @Override
        public void onDestroy() {
            super.onDestroy();
            cancelRequest();
            mCompositeSubscription.clear();
        }
    };

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

        if (ReqeustModel.CHAIN == observer.getReqeustModel()) {
            prepareChain(array);
            initiate(observer, array.get(0));
        } else if (ReqeustModel.PERFORMANCE == observer.getReqeustModel()) {
            synInitiate(observer, array);
        }
    }


    /*准备工作，对数据做一些必要的数据处理,在这里链这个请求链*/
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

    /*发起请求*/
//    <R extends IChainRequest>
    void initiate(final C observer, final R request) {
        LogUtil.e("------------ initiate  a  request ------------");
        if (request == null) {
            observer.onChainEnd();
            return;
        }
        if (!request.hasPre()) observer.onChainStart();

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
                        observer.onNotifyDataProcess(r.getId(), r);

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
                        observer.onNotifyResponse(request.getId(), request);
//                        initiate(observer, request.getNext());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("Observer -----> onError");
                        observer.onError(e);
                        observer.onChainEnd();
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.e("Observer -----> onComplete");
                    }
                });
    }


    /*性能模式(理论上整个请求耗时=最慢请求耗时) - 所有请求同步进行,请求回复顺序会错乱(*使用此请求方式会使链式请求模式失效)*/
    void synInitiate(final C observer, final List<R> request) {
        List<Observable<R>> observable_array = new ArrayList<>();
        for (final R r : request) {
            observable_array.add(Observable.create(new ObservableOnSubscribe<R>() {
                        @Override
                        public void subscribe(ObservableEmitter<R> emitter) {
                            try {
                                if (isDebug) r.showReqeustLog();
                                mRequestHelper.perform(r);
                                r.completed();
                                if (isDebug) r.showResponseLog();

                                observer.onNotifyDataProcess(r.getId(), r);
                                emitter.onNext(r);
                                emitter.onComplete();
                            } catch (Exception e) {
                                if (!emitter.isDisposed()) emitter.onError(e);
                                else if (isDebug) e.printStackTrace();
                            }
                        }
                    })
                            .subscribeOn(Schedulers.io())
            );
        }

        Observable.mergeDelayError(observable_array, maxConcurrency)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<R>() {
                    Disposable mDisposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeSubscription.add(d);
                        observer.onChainStart();
                        mDisposable = d;
                    }


                    @Override
                    public void onNext(R r) {
                        observer.onNotifyResponse(r.getId(), r);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mCompositeSubscription.remove(mDisposable);
                        observer.onError(e);
                        observer.onChainEnd();
                    }

                    @Override
                    public void onComplete() {
                        mCompositeSubscription.remove(mDisposable);
                        observer.onComplete();
                        observer.onChainEnd();
                    }
                });

    }


}
