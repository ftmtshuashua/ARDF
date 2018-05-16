package com.lfp.ardf.module.net;

import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.framework.util.SimpleLifeCycleObserve;
import com.lfp.ardf.module.net.i.IChainRequest;
import com.lfp.ardf.module.net.i.IChainResponseObserver;

import java.util.ArrayList;
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
public class ChainReqeustManager<T extends IChainRequest> {

    public ChainReqeustManager(IAppFramework appfk) {
        appfk.registeredObserve(mContentObserve);
    }

    /**
     * 发起请求
     *
     * @param observer 请求回复接受者
     * @param array    请求链
     * @return ChainReqeustManager
     */
    public ChainReqeustManager request(IChainResponseObserver observer, T... array) {
        if (array == null || array.length == 0) return this;
        List<T> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            T request = array[i];
            if (request != null) {
                request.setId(i);
                list.add(request);
            }
        }
        return request(observer, list);
    }

    /**
     * 发起请求
     *
     * @param observer 请求回复接受者
     * @param array    请求链
     * @return ChainReqeustManager
     */
    public ChainReqeustManager request(IChainResponseObserver observer, List<T> array) {
        if (array == null || array.isEmpty()) return this;
        for (int i = array.size() - 1; i >= 0; i--) {
            T request = array.get(i);
            if (request == null) array.remove(i);
            else {
                if (!request.isIdHave()) request.setId(i);
            }
        }

        prepare(array);
        initiate(observer, array.get(0));
        return this;
    }


    SimpleLifeCycleObserve mContentObserve = new SimpleLifeCycleObserve() {
        @Override
        public void onDestroy() {
            super.onDestroy();

        }
    };

    /*准备工作，对数据做一些必要的数据处理*/
    void prepare(List<T> array) {
        for (int i = 1; i < array.size() - 1; i++) {
            T request = array.get(i);
            T pre = array.get(i - 1);
            T next = array.get(i + 1);

            pre.setNext(request);
            request.setPre(pre);
            request.setNext(next);
            next.setPre(request);
        }
    }

    /*发起请求*/
    <T extends IChainRequest> void initiate(final IChainResponseObserver observer, final T request) {
        if (request == null) {
            observer.onChainComplete();
            return;
        }

        ObservableOnSubscribe<T> request_observable = new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {


                emitter.onComplete();
            }
        };


        Observable.create(request_observable)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap(new Function<T, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(T t) throws Exception {


//                        return Observable.error(new NullPointerException());

                        return Observable.just(null);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T t) {
                        observer.onResponse(request.getId(), request);
                        initiate(observer, request.getNext());
                    }

                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                        observer.onChainComplete();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
