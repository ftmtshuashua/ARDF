package com.lfp.ardf.module.net;

import com.lfp.ardf.module.net.i.IChainRequestHelper;
import com.lfp.ardf.module.net.i.IRequestClient;

import java.io.IOException;

/**
 * Okhttp 实现的请求者
 * Created by LiFuPing on 2018/5/17.
 */
public class OkHttpRequestHelper implements IChainRequestHelper<OkHttpRequest> {
    IRequestClient mRequetClient;

    public OkHttpRequestHelper() {
        this(OkHttpRequestClient.getInstace());
    }

    public OkHttpRequestHelper(IRequestClient client) {
        mRequetClient = client;
    }

    @Override
    public Object perform(OkHttpRequest request) throws Exception {
        return mRequetClient.perform(request);
    }

    @Override
    public void cancel(OkHttpRequest request) {
        mRequetClient.cancel(request);
    }

}
