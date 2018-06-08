package com.lfp.ardf.module.net.i;

/**
 * 请求监测器<br/>
 * 用于监控整个请求链与其中请求当状态
 * <p>
 * Created by LiFuPing on 2018/6/8.
 */
public interface IRequestMonitor {

    /**
     * 当请求开始之前它应该调用onStart(),通知观察者.
     *
     *
     */
    void onStart();

    /**
     * 通知请求完成
     */
    void onEnd();
}
