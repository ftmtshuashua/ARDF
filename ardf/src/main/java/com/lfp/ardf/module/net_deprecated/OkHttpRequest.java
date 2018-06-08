package com.lfp.ardf.module.net_deprecated;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.module.net_deprecated.client.OkHttpReqeuestClient;
import com.lfp.ardf.module.net_deprecated.request.ImpChainRequest;
import com.lfp.ardf.module.net_deprecated.util.UrlFormat;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Okhttp请求 <br/>
 * Created by LiFuPing on 2018/5/28.
 */
public class OkHttpRequest extends ImpChainRequest implements OkHttpReqeuestClient.OkHttpRequestHolper {

    /**
     * 发起请求的请求地址
     */
    String api;
    /**
     * Post请求参数
     */
    HashMap<String, Object> requestBodyFrom;
    /**
     * OkHttp请求
     */
    Request mRequest;
    /**
     * 请求回复数据
     */
    Response response;
    /**
     * 回复内容
     */
    String body;

    Call call;


    int mFlag;

    public OkHttpRequest(String api) {
        setApi(api);
    }

    /**
     * 获得API
     *
     * @return 返回API
     */
    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    @Override
    public void showRequestLog() {
        Request reqeust = buildRequest();
        LogUtil.w_Pretty(
                MessageFormat.format("ID:{4},Method:{0},Api:{1},剩余请求:{2},是否忽略回复;{3}"
                        , reqeust.method(), getApi(), getRemainingCount(), isIgnoreResponse(), getId())
        );
    }

    @Override
    public void showResponseLog() {
        Response response = this.response;
        if (response == null) {
            LogUtil.e_Pretty(
                    MessageFormat.format("ID:{0},Method:{1},Api:{2} - No reply!"
                            , getId(), mRequest.method(), getApi())
            );
            return;
        }

        long time = Long.parseLong(response.header("OkHttp-Received-Millis")) - Long.parseLong(response.header("OkHttp-Sent-Millis"));
        String format = "ID:{0},Method:{1},Api:{2} ,Protocol:{3},IsRedirect:{4}\nHttpCode:{5},耗时:{7}ms,Msg:{6}\n\n{8}";
        String msg = MessageFormat.format(format
                , getId(), mRequest.method(), getApi()
                , response.protocol().toString(), response.isRedirect()
                , response.code(), response.message(), time
                , getResponseBody()
        );

        if (isSuccessful()) LogUtil.i_Pretty(msg);
        else LogUtil.e_Pretty(msg);
    }

    @Override
    public void cancel() {
        if (call == null || call.isCanceled()) return;
        call.cancel();
    }

    /**
     * 构建请求
     *
     * @return
     */
    @Override
    public Request buildRequest() {
        Request.Builder bulder = new Request.Builder();
        bulder.url(new UrlFormat(api).toEncodeUrl());
        return mRequest = bulder.build();
    }

    @Override
    public void setCall(Call call) {
        this.call = call;
    }

    @Override
    public void setResponse(Response response) throws IOException {
        this.response = response;
        body = response.body().string();
    }

    /**
     * 获得回复body信息
     */
    public String getResponseBody() {
        return body;
    }

    /*判断请求已完成*/
    public boolean isSuccessful() {
        return response != null && response.isSuccessful();
    }
}
