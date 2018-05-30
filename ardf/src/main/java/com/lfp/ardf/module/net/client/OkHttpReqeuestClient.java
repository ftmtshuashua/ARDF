package com.lfp.ardf.module.net.client;

import com.lfp.ardf.module.net.request.IChainRequest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Okhttp请求客户端<br/>
 * Created by LiFuPing on 2018/5/28.
 */
public class OkHttpReqeuestClient<T extends OkHttpReqeuestClient.OkHttpRequestHolper> implements IRequestClient<T> {
    static OkHttpReqeuestClient mInstacel;
    OkHttpClient mHttpClient;

    private OkHttpReqeuestClient() {
        this(new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.MINUTES)
                .build());
    }

    /**
     * @param client 自定义请求实例
     */
    public OkHttpReqeuestClient(OkHttpClient client) {
        mHttpClient = client;
    }

    /**
     * @return 返回默认请求客户端
     */
    public static final OkHttpReqeuestClient getDefualt() {
        if (mInstacel == null) {
            synchronized (OkHttpReqeuestClient.class) {
                if (mInstacel == null) {
                    mInstacel = new OkHttpReqeuestClient();
                }
            }
        }
        return mInstacel;
    }

    @Override
    public void perform(T reqeust) throws Exception {
        if (reqeust.getId() != 1) Thread.sleep(300);

        Call call = mHttpClient.newCall(reqeust.buildRequest());
        reqeust.setCall(call);
        Response response = call.execute();
        reqeust.setResponse(response);
    }


    /**
     * OkHttp实现接口
     */
    public interface OkHttpRequestHolper extends IChainRequest {
        /**
         * 构建请求
         *
         * @return
         */
        Request buildRequest();

        void setCall(Call call);

        /**
         * @param response 设置请求回复结果
         */
        void setResponse(Response response) throws IOException;
    }
}
