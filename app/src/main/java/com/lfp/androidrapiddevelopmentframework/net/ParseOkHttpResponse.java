package com.lfp.androidrapiddevelopmentframework.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.lfp.ardf.module.net.OkHttpRequest;
import com.lfp.ardf.util.ToastUtil;

import org.json.JSONObject;

/**
 * 解析请求回复<br/>
 * Created by LiFuPing on 2018/6/22.
 */
public class ParseOkHttpResponse {
    public static final int REQUEST_SUCCESS = 0;
    public static final int REQUEST_ERROR_JSONPARSE = 1000; /*Json解析错误*/

    String body;
    int c;
    String m;
    String d;

    public ParseOkHttpResponse(OkHttpRequest request) {
        body = request.body();
        try {
            JSONObject jo = new JSONObject(body);
            c = jo.getInt("c");
            m = jo.getString("m");
            d = jo.getString("d");
        } catch (Exception e) {
            c = REQUEST_ERROR_JSONPARSE;
            m = e.getMessage();
        }
    }


    /**
     * 判断请求是否成功
     *
     * @param isErrorToast 是否Toast错误信息
     * @return
     */
    public boolean isSuccess(boolean isErrorToast) {
        boolean success = getCode() == REQUEST_SUCCESS;
        if (isErrorToast && !success && !TextUtils.isEmpty(getMsg())) ToastUtil.show(getMsg());
        return success;
    }

    public int getCode() {
        return c;
    }

    public String getMsg() {
        return m;
    }

    public String getData() {
        return d;
    }

    public <T> T getData(Class<T> cls) {
        return new Gson().fromJson(getData(), cls);
    }
}
