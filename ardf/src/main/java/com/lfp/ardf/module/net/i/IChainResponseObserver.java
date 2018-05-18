package com.lfp.ardf.module.net.i;

/**
 * 链式请求观测者
 * Created by LiFuPing on 2018/5/15.
 */
public interface IChainResponseObserver<R extends IChainRequest> {

    /**
     * 请求链开始运作
     */
    void onChainStart();

    /**
     * 出现错误的时候调用 与 onComplete() 方法互斥
     */
    void onError(Throwable e);

    /**
     * 当所有请求全部成功的时候调用 与 onError() 方法互斥
     */
    void onComplete();

    /**
     * 在这里处理一些耗时的操作
     */
    void onNotifyDataProcess(int id, R request);

    /**
     * 请求完成时调用
     *
     * @param id
     * @param request
     */
    void onNotifyResponse(int id, R request);

    /**
     * 无论是onComplete()还是onError()之后都会回调此方法，在这里做一些必要的回收操作
     */
    void onChainEnd();

    /**
     * 启动这个选项将大大增加请求消耗的事件，建议不必要时不要开启
     *
     * @return 是否禁用并发请求
     */
    boolean isDisableConcurrentRequest();

}
