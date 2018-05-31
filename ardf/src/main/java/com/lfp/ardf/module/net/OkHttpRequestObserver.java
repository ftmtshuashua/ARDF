package com.lfp.ardf.module.net;

import com.lfp.ardf.module.net.observer.IRequestObserver;
import com.lfp.ardf.module.net.util.ExceptionTotalUtil;
import com.lfp.ardf.util.ToastUtil;

/**
 * 请求观察者<br/>
 * Created by LiFuPing on 2018/5/28.
 */
public abstract class OkHttpRequestObserver implements IRequestObserver<OkHttpRequest> {
    @Override
    public void onStart() {
    }

    @Override
    public void onError(OkHttpRequest request, Throwable e) {
        String massage = ExceptionTotalUtil.getThrowableToastInfo(e);
        ToastUtil.show(massage);
    }

    @Override
    public void onComputation(OkHttpRequest request) {
    }

    @Override
    public final void onResponse(OkHttpRequest request) {
        if (request.isIgnoreResponse()) return;
        onRequestResponse(request);
    }

    /**
     * 请求成功之后回调接口
     *
     * @param request 请求和回复信息
     */
    public abstract void onRequestResponse(OkHttpRequest request);

    @Override
    public void onEnd() {
    }
}
