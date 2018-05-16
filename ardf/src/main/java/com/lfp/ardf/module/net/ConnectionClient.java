package com.lfp.ardf.module.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by LiFuPing on 2017/9/5.
 */

public class ConnectionClient {
    private static ConnectionClient mInstace;
    OkHttpClient mHttpClient;

    private ConnectionClient() {
        mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.MINUTES)
                .readTimeout(35, TimeUnit.MINUTES)
                .build();
    }

    public static final ConnectionClient getInstace() {
        if (mInstace == null) {
            synchronized (ConnectionClient.class) {
                if (mInstace == null) {
                    mInstace = new ConnectionClient();
                }
            }
        }
        return mInstace;
    }

    public OkHttpClient getHttpClient() {
        return mHttpClient;
    }
}
