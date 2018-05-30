package com.lfp.ardf.module.net;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.module.net.observer.IRequestObserver;
import com.lfp.ardf.module.net.util.ExceptionTotalUtil;
import com.lfp.ardf.util.ToastUtil;

import java.text.MessageFormat;

/**
 * 请求观察者<br/>
 * Created by LiFuPing on 2018/5/28.
 */
public abstract class OkHttpRequestObserver implements IRequestObserver<OkHttpRequest> {
    @Override
    public void onStart() {
        LogUtil.e("--->> onStart()");
    }

    @Override
    public void onError(OkHttpRequest request, Throwable e) {
        LogUtil.e(MessageFormat.format("接收到异常信息:{0} -  Exception：{1}", request.getId(), e.getMessage()));
//        if (ExceptionTotalUtil.isShowThrowableToast(e)) {
        String massage = ExceptionTotalUtil.getThrowableToastInfo(e);
        ToastUtil.show(massage);
//        }
    }

    @Override
    public void onComputation(OkHttpRequest request) {
        LogUtil.e("--->> onComputation()");
    }

    @Override
    public final void onResponse(OkHttpRequest request) {
//        LogUtil.e(MessageFormat.format("--->> onResponse() -  isIgnoreResponse:{0}", request.isIgnoreResponse()));
        if (request.isIgnoreResponse()) return;
        onRequestResponse(request);
    }

    public abstract void onRequestResponse(OkHttpRequest request);

    @Override
    public void onEnd() {
        LogUtil.e("--->> onEnd()");
    }
}
