package com.lfp.androidrapiddevelopmentframework.net;

import android.content.Context;

import com.lfp.androidrapiddevelopmentframework.dialog.ProgressDelayDialog;
import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.module.net_deprecated.OkHttpRequest;
import com.lfp.ardf.module.net_deprecated.OkHttpRequestObserver;

import java.text.MessageFormat;

/**
 * 统一请求回复处理<br/>
 * Created by LiFuPing on 2018/5/31.
 */
public abstract class UnifyResponse extends OkHttpRequestObserver {
    ProgressDelayDialog mDialog;
    long start_time;
    long expend_time;
    Context context;

    public UnifyResponse(Context c) {
        context = c;
        mDialog = new ProgressDelayDialog((IAppFramework) context);
    }

    @Override
    public void onStart() {
        super.onStart();
        start_time = System.currentTimeMillis();
        mDialog.show();
        onLog("onStart()");
    }

    @Override
    public void onEnd() {
        super.onEnd();
        mDialog.dismiss();
        expend_time = System.currentTimeMillis() - start_time;
        onLog(MessageFormat.format("\nonEnd() - 耗时:{0,number,0}ms", getExpendTime()));
    }

    public long getExpendTime() {
        return expend_time;
    }

    @Override
    public void onComputation(OkHttpRequest request) {
        super.onComputation(request);
//        try{
//            Thread.sleep(1500);
//        }catch (Exception e){}
    }

    @Override
    public void onRequestResponse(OkHttpRequest request) {
        onLog(String.format("\nID:%s - %s\n%s", request.getId(), request.getApi(), request.getResponseBody()));
    }

    protected void onLog(String str) {

    }

}
