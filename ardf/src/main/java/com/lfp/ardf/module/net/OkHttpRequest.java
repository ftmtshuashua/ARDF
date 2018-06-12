package com.lfp.ardf.module.net;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.exception.NetStateException;
import com.lfp.ardf.module.net.i.IRequest;
import com.lfp.ardf.module.net.i.RequestListener;
import com.lfp.ardf.module.net.i.SimpleRequest;
import com.lfp.ardf.module.net_deprecated.util.UrlFormat;

import java.text.MessageFormat;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <br/>
 * Created by LiFuPing on 2018/6/11.
 */
public class OkHttpRequest extends SimpleRequest implements RequestListener {

    /*忽略请求结果*/
    static final int FLAG_IGNORE_RESPONSE = 0x1;

    String api;
    Call mCall;
    int flag;

    String body;


    public OkHttpRequest(String api) {
        super.setRequestListener(this);
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

        showLog(request, response);

        if (!response.isSuccessful())
            throw new NetStateException(MessageFormat.format("{0}{1}", response.code(), response.message()));
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
    public void cancel() {
        if (mCall != null) mCall.cancel();
    }

    public boolean isIgnoreComplete() {
        return (flag & FLAG_IGNORE_RESPONSE) != 0;
    }

    public OkHttpRequest setIgnoreComplete(boolean ignore) {
        if (ignore) flag |= FLAG_IGNORE_RESPONSE;
        else flag &= ~FLAG_IGNORE_RESPONSE;
        return this;
    }

    RequestListener mRequestListener;

    @Override
    public void setRequestListener(RequestListener l) {
        mRequestListener = l;
    }

    @Override
    public void onStart(IRequest request) {
        if (mRequestListener != null) mRequestListener.onStart(request);
    }

    @Override
    public void onError(IRequest request, Throwable e) {
        if (mRequestListener != null) mRequestListener.onError(request, e);
    }

    @Override
    public void onResponse(IRequest request) {
        if (mRequestListener != null) mRequestListener.onResponse(request);
    }

    @Override
    public void onComplete(IRequest request) {
        if (mRequestListener != null && !isIgnoreComplete()) mRequestListener.onComplete(request);
    }

    @Override
    public void onEnd(IRequest request) {
        if (mRequestListener != null) mRequestListener.onEnd(request);
    }
}
