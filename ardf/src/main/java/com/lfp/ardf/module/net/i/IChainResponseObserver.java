package com.lfp.ardf.module.net.i;

/**
 * 链式请求观测者
 * Created by LiFuPing on 2018/5/15.
 */
public interface IChainResponseObserver<R extends IChainRequest> {
    /**
     * 请求模式 (PERFORMANCE,CHAIN)
     */
    enum ReqeustModel {
        /**
         * 性能模式<br/>
         * 该模式下的所有请求并发执行,理论上整个请求耗时=最慢请求耗时.<br/>
         * 请求回复顺序会错乱(*使用此请求方式会使链式请求功能模式失效)
         */
        PERFORMANCE,

        /**
         * 链式请求模式<br/>
         * 该模式下的请求会按照传入顺序依次执行,在这种模式下后一个请求必须等待前一个请求执行完成之后才开始执行
         */
        CHAIN
    }

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
     * 终止请求链之后,后面的请求会被舍弃,并且结束这个请求链！
     *
     * @return 返回这个请求的运行状态
     */
    boolean isShutdown();

    /**
     * @return 返回当前请求的请求模式
     */
    ReqeustModel getReqeustModel();

}
