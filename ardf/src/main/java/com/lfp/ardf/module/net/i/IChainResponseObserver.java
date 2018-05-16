package com.lfp.ardf.module.net.i;

/**
 * 链式请求观测者
 * Created by LiFuPing on 2018/5/15.
 */
public interface IChainResponseObserver {

    /**
     * 出现错误的时候调用
     */
    void onError(Throwable e);

    /**
     * 当请求链结束时调用
     */
    void onChainComplete();


    /**在这里处理一些耗时的操作*/
    void onResponseComputation(int id, IChainRequest request);

    /**
     * 请求完成时调用
     *
     * @param id
     * @param request
     */
    void onResponse(int id, IChainRequest request);
}
