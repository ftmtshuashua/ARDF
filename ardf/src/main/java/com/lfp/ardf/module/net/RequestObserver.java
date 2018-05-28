package com.lfp.ardf.module.net;

import com.lfp.ardf.module.net.observer.IRequestObserver;

/**
 * 请求观察者<br/>
 * Created by LiFuPing on 2018/5/28.
 */
public class RequestObserver implements IRequestObserver<HttpClientRequest> {
    @Override
    public void onStart() {

    }

    @Override
    public void onError(HttpClientRequest request, Exception e) {

    }

    @Override
    public void onComputation(HttpClientRequest request) {

    }

    @Override
    public void onResponse(HttpClientRequest request) {

    }

    @Override
    public void onEnd() {

    }
}
