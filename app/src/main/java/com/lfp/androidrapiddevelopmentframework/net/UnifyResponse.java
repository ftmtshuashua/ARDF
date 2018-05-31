package com.lfp.androidrapiddevelopmentframework.net;

import android.content.Context;

import com.lfp.androidrapiddevelopmentframework.dialog.ProgressDelayDialog;
import com.lfp.ardf.module.net.OkHttpRequestObserver;

/**
 * 统一请求回复处理<br/>
 * Created by LiFuPing on 2018/5/31.
 */
public abstract class UnifyResponse extends OkHttpRequestObserver {
    ProgressDelayDialog mDialog;

    Context context;

    public UnifyResponse(Context c) {
        context = c;
        mDialog = new ProgressDelayDialog(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        mDialog.show();
    }

    @Override
    public void onEnd() {
        super.onEnd();
        mDialog.dismiss();
    }

}
