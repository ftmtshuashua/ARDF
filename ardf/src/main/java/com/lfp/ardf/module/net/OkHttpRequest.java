package com.lfp.ardf.module.net;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.module.net.client.OkHttpReqeuestClient;
import com.lfp.ardf.module.net.request.ImpChainRequest;
import com.lfp.ardf.module.net.util.UrlFormat;

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
public class OkHttpRequest extends ImpChainRequest<OkHttpRequest> implements OkHttpReqeuestClient.OkHttpRequestHolper {

    /**
     * 突破Loger单行数据限制,显示完整的Body信息
     */
    public static final int FLAG_SHOW_LONG_BODY_LOG = 0x1 << 1;

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
        this.api = api;
    }

    /**
     * 获得API
     *
     * @return 返回API
     */
    public String getApi() {
        return api;
    }

    /**
     * @param is 是否显示完整body信息
     * @return OkHttpRequest
     */
    public OkHttpRequest setShowLongBodyLog(boolean is) {
        if (is) mFlag |= FLAG_SHOW_LONG_BODY_LOG;
        else mFlag &= ~FLAG_SHOW_LONG_BODY_LOG;
        return this;
    }

    @Override
    public void showRequestLog() {
        Request reqeust = buildRequest();
        LogUtil.w(MessageFormat.format("OkHttpRequest --->>\nID:{4},Method:{0},Api:{1},剩余请求:{2},是否忽略回复;{3}"
                , reqeust.method(), getApi(), getRemainingCount(), isIgnoreResponse(), getId()));
    }

    @Override
    public void showResponseLog() {
        Response response = this.response;
        if (response == null) {
            LogUtil.e(MessageFormat.format("OkHttpResponse <<---<<\nID:{0},Method:{1},Api:{2} - No reply!"
                    , getId(), mRequest.method(), getApi()));
            return;
        }

        String msg;
        long time = Long.parseLong(response.header("OkHttp-Received-Millis")) - Long.parseLong(response.header("OkHttp-Sent-Millis"));
        if ((mFlag & FLAG_SHOW_LONG_BODY_LOG) != 0) {
            String format = "OkHttpResponse <<---<<\nID:{0},Method:{1},Api:{2} ,Protocol:{3},IsRedirect:{4}\nCode:{5},耗时:{7}ms,Msg:{6}";
            msg = MessageFormat.format(format
                    , getId(), mRequest.method(), getApi()
                    , response.protocol().toString(), response.isRedirect()
                    , response.code(), response.message(), time
            );
        } else {
            String format = "OkHttpResponse <<---<<\nID:{0},Method:{1},Api:{2} ,Protocol:{3},IsRedirect:{4}\nCode:{5},耗时:{7}ms,Msg:{6}\nBody[{8}]";
            msg = MessageFormat.format(format
                    , getId(), mRequest.method(), getApi()
                    , response.protocol().toString(), response.isRedirect()
                    , response.code(), response.message(), time
                    , getResponseBody()
            );
        }

        if (isSuccessful()) LogUtil.i(msg);
        else LogUtil.e(msg);

        if ((mFlag & FLAG_SHOW_LONG_BODY_LOG) != 0) {
            LogUtil.e("------------- Long body -------------");
            LogUtil.longStr(getResponseBody());
        }
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
