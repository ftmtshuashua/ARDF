package com.lfp.androidrapiddevelopmentframework.net;

import com.lfp.androidrapiddevelopmentframework.dialog.ProgressDelayDialog;
import com.lfp.androidrapiddevelopmentframework.dialog.ProgressDialog;
import com.lfp.ardf.dialog.BaseDialog;
import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.module.net.i.RequestListener;
import com.lfp.ardf.module.net.i.RequestNode;
import com.lfp.ardf.module.net.util.ExceptionTotalUtil;

/**
 * 请求统一控制 <br/>
 * Created by LiFuPing on 2018/6/15.
 */
public abstract class UnifyRespond<R extends RequestNode> implements RequestListener<R> {
    /*请求ProgressDialog*/
    public static final int FLAG_SHOW_DIALOG = 0x1;
    /*请求ProgressDialog - 带延时*/
    public static final int FLAG_SHOW_DELAY_DIALOG = 0x2;
    /*显示错误信息*/
    public static final int FLAG_SHOW_ERROR_MSG = 0x1 << 2;

    static final int FLAG_DIALOG_MASK = 0x2;


    int mFlag;
    BaseDialog mDialog;

    public UnifyRespond(IAppFramework appfk) {
        this(appfk, FLAG_SHOW_DELAY_DIALOG | FLAG_SHOW_ERROR_MSG);
    }

    public UnifyRespond(IAppFramework appfk, int flag) {
        this.mFlag |= flag;
        if (appfk != null) {
            switch (mFlag & FLAG_DIALOG_MASK) {
                case FLAG_SHOW_DELAY_DIALOG:
                    mDialog = new ProgressDelayDialog(appfk);
                    break;
                case FLAG_SHOW_DIALOG:
                    mDialog = new ProgressDialog(appfk);
                    break;
            }
        }
    }


    @Override
    public void onStart(RequestNode request) {
        if (mDialog != null) mDialog.show();
    }

    @Override
    public void onError(R request, Throwable e) {
        if ((mFlag & FLAG_SHOW_ERROR_MSG) != 0) {
            ExceptionTotalUtil.show(e);
        }
    }

    @Override
    public void onResponse(R request) {

    }

    @Override
    public void onEnd(RequestNode request) {
        if (mDialog != null) mDialog.dismiss();
    }
}
