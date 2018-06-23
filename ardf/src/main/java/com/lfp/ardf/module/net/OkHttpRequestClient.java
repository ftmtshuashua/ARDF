package com.lfp.ardf.module.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * OkHttp请求客户端 <br>
 * Created by LiFuPing on 2018/6/11.
 */
public class OkHttpRequestClient {
    private static OkHttpRequestClient mInstance;
    OkHttpClient mHttpClient;

    public OkHttpRequestClient() {
        mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.MINUTES)
                .build();
    }

    public static final OkHttpRequestClient getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpRequestClient.class) {
                if (mInstance == null) mInstance = new OkHttpRequestClient();
            }
        }

        return mInstance;
    }

}
