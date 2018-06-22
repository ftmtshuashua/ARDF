package com.lfp.ardf.module.net;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.exception.NetStateException;
import com.lfp.ardf.module.net.i.RequestNode;
import com.lfp.ardf.module.net.imp.RequestCall;
import com.lfp.ardf.module.net.util.UrlFormat;

import java.text.MessageFormat;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <br/>
 * Created by LiFuPing on 2018/6/11.
 */
public class OkHttpRequest extends RequestCall {
    /*忽略请求结果*/
    static final int FLAG_IGNORE_RESPONSE = 0x1;

    String api;
    Call mCall;
    int flag;

    String body;


    public OkHttpRequest(String api) {
        setApi(api);
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getApi() {
        return api;
    }

    public String body() {
        return body;
    }

    /**
     * 当请求完成,回调此接口
     *
     * @param response 请求回复数据
     * @throws Exception 数据处理时的异常信息
     */
    protected void onResponse(Response response) throws Exception {
    }

    @Override
    protected synchronized void call() throws Exception {
        Request request = new Request.Builder().url(new UrlFormat(api).toEncodeUrl()).build();
        LogUtil.w_Pretty(
                MessageFormat.format("ID:{0},Method:{1},Api:{2},忽略回复;{3}"
                        , getId(), request.method(), getApi(), isIgnoreComplete())
        );
        mCall = OkHttpRequestClient.getInstance().mHttpClient.newCall(request);
        Response response = mCall.execute();
        body = response.body().string();
        onResponse(response);

        showLog(request, response);

        if (!response.isSuccessful())
            throw new NetStateException(MessageFormat.format("{0}{1}", response.code(), response.message()));
    }

    @Override
    public void cancel() {
        if (mCall != null) mCall.cancel();
    }

    public boolean isIgnoreComplete() {
        return (flag & FLAG_IGNORE_RESPONSE) != 0;
    }

    /**
     * 设置忽略请求回复
     *
     * @param ignore 为true的时候将不会回调onComplete方法
     * @return
     */
    public OkHttpRequest setIgnoreComplete(boolean ignore) {
        if (ignore) flag |= FLAG_IGNORE_RESPONSE;
        else flag &= ~FLAG_IGNORE_RESPONSE;
        return this;
    }

    void showLog(Request request, Response response) {
        if (response == null) {
            LogUtil.e_Pretty(
                    MessageFormat.format("ID:{0},Method:{1},Api:{2} - No reply!"
                            , getId(), request.method(), getApi())
            );
            return;
        }

        long time = Long.parseLong(response.header("OkHttp-Received-Millis")) - Long.parseLong(response.header("OkHttp-Sent-Millis"));
        String format = "ID:{0},Method:{1},Api:{2} ,Protocol:{3},IsRedirect:{4}\nHttpCode:{5},耗时:{7}ms,Msg:{6}\n\n{8}";
        String msg = MessageFormat.format(format
                , getId(), request.method(), getApi()
                , response.protocol().toString(), response.isRedirect()
                , response.code(), response.message(), time
                , body
        );

        if (isSuccessful()) LogUtil.i_Pretty(msg);
        else LogUtil.e_Pretty(msg);
    }

    @Override
    protected void notifyComplete(RequestNode request) {
        if (isIgnoreComplete() && request == this) return;
        super.notifyComplete(request);
    }
}
