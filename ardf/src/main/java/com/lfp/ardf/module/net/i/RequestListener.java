package com.lfp.ardf.module.net.i;

/**
 * 请求监测器<br/>
 * 用于监控整个请求链与其中请求当状态
 * <p>
 * Created by LiFuPing on 2018/6/8.
 */
public interface RequestListener {
    /**
     * 当请求开始之前它应该调用onStart(),通知观察者.
     */
    void onStart(IRequest request);

    /**
     * 当某个请求发生异常时回调
     *
     * @param request 发生异常的请求
     * @param e       异常信息
     */
    void onError(IRequest request, Throwable e);

    void onResponse(IRequest request);

    void onComplete(IRequest request);

    /**
     * 通知请求完成
     */
    void onEnd(IRequest request);
}
