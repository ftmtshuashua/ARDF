package com.lfp.ardf.module.net.observer;

import com.lfp.ardf.module.net.request.IRequest;

/**
 * 请求结果接口<br/>
 * Created by LiFuPing on 2018/5/28.
 */
public interface IRequestObserver<R extends IRequest> {

    void onStart();

    /**
     * 当请求发生异常时回调
     *
     * @param request 发生异常的请求
     * @param e       异常信息
     */
    void onError(R request, Exception e);

    /**
     * 这个方法回调在子线程，可以在这里做一些耗时操作
     */
    void onComputation(R request);

    void onResponse(R request);

    void onEnd();
}
