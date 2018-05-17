package com.lfp.ardf.module.net;

import com.lfp.ardf.module.net.imp.ImpChainReqeust;
import com.lfp.ardf.module.net.util.UrlFormat;

import java.text.MessageFormat;
import java.util.HashMap;

import okhttp3.Request;

/**
 * 适用于OkHttp的请求
 * Created by LiFuPing on 2018/5/17.
 */
public class OkHttpRequest extends ImpChainReqeust {

    /**
     * 标记忽略请求回复状态,包含这个标记的请求,顶层用户不会收到Response回复结果
     */
    public static final int FLAG_IGNORE_RESPONSE = 0x1;

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

    public void setFlag(int flg) {
        mFlag |= flg;
    }

    /**
     * 忽略当前请求的请求结果
     *
     * @param is 是否忽略当前请求的结果回复
     * @return OkHttpRequest
     */
    public OkHttpRequest setIgnoreResponse(boolean is) {
        if (is) setFlag(FLAG_IGNORE_RESPONSE);
        else mFlag &= ~FLAG_IGNORE_RESPONSE;
        return this;
    }

    /**
     * @return 返回是否忽略这个请求的结果
     */
    public boolean isIgnoreResponse() {
        return (mFlag & FLAG_IGNORE_RESPONSE) != 0;
    }

    /**
     * 构建请求
     *
     * @return
     */
    public Request buildRequest() {
        Request.Builder bulder = new Request.Builder();
        bulder.url(new UrlFormat(api).toEncodeUrl());
        return mRequest = bulder.build();
    }

    @Override
    public String toString() {
        return MessageFormat.format("OkHttpRequest[method:{0},Api:{1}],剩余请求:{2},忽略回复;{3}", mRequest == null ? "" : mRequest.method(), getApi(), getRemainingCount(), isIgnoreResponse());
    }
}
