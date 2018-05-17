package com.lfp.ardf.module.net;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.module.net.i.IRequestClient;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * OKHttp请求客户端配置
 * Created by LiFuPing on 2018/5/17.
 */
public class OkHttpRequestClient implements IRequestClient {
    static OkHttpRequestClient mInstacel;
    OkHttpClient mHttpClient;

    private OkHttpRequestClient() {
        mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.MINUTES)
                .build();
    }

    public static final OkHttpRequestClient getInstace() {
        if (mInstacel == null) {
            synchronized (OkHttpRequestClient.class) {
                if (mInstacel == null) {
                    mInstacel = new OkHttpRequestClient();
                }
            }
        }
        return mInstacel;
    }

    @Override
    public Object perform(OkHttpRequest request) throws IOException {
        Call mcall = mHttpClient.newCall(request.buildRequest());
        Response response = mcall.execute();

        LogUtil.e(MessageFormat.format("请求结果:{0}", response.code()));
        return null;
    }

    @Override
    public void cancel() {

    }


//        getInstace().newCall(null).cancel();/*取消请求*/
        /*
        如果还未和服务器建立连接:
        回调：void onFailure(Call call, IOException e);
        自己主动取消的错误的 java.net.SocketException: Socket closed
        超时的错误是 java.net.SocketTimeoutException
        网络出错的错误是java.net.ConnectException: Failed to connect to xxxxx

        在onResponse的时候刚好cancel网络请求:
        会在onResponse()方法中抛出java.net.SocketException: Socket closed

         */
}
