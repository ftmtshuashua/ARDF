package com.lfp.ardf.module.net;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.module.net.i.IRequestClient;

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

    Call call;

    @Override
    public Object perform(OkHttpRequest request) throws Exception {
        try {
            Thread.sleep(300);
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        }
        LogUtil.e("执行请求..");
        call = mHttpClient.newCall(request.buildRequest());
        Response response = call.execute();
        request.setResponse(response);
        LogUtil.e("执行请求结束..");
        return null;
    }

    @Override
    public void cancel(OkHttpRequest request) {
        LogUtil.e("准备强制取消网络请求..");
        if (call != null && call.isExecuted()) {
            LogUtil.e("成功强制取消网络请求..");
            call.cancel();
        }
    }
}
