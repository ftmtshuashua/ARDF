package com.lfp.ardf.module.net_deprecated.observer;

import com.lfp.ardf.module.net_deprecated.request.IRequest;

/**
 * 请求结果接口<br/>
 * Created by LiFuPing on 2018/5/28.
 */
public interface IRequestObserver<R extends IRequest> {

    /**
     * 请求开始当时候回调,在这里做一些初始化动作,比如弹出一个等待对话框
     */
    void onStart();

    /**
     * 当某个请求发生异常时回调
     *
     * @param request 发生异常的请求
     * @param e       异常信息
     */
    void onError(R request, Throwable e);

    /**
     * 一个请求完成之后会回调该方法,
     * 这个方法回调在子线程,
     * 在这里进行耗时数据处理操作不会影响到UI线程
     */
    void onComputation(R request);


    /**
     * 一个请求完成之后回调该方法,
     * 数据经过onComputation()处理之后
     * @param request
     */
    void onResponse(R request);

    /**
     * 所有请求结束时回调,在这里做一些回收动作,比如关闭等待对话框
     */
    void onEnd();
}
