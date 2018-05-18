package com.lfp.ardf.module.net.util;

import android.content.Context;
import android.text.TextUtils;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.exception.MsgException;
import com.lfp.ardf.exception.NetStateException;
import com.lfp.ardf.util.ToastUtil;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.MessageFormat;

/**
 * 请求异常处理
 * Created by LiFuPing on 2017/9/6.
 */

public class ReqeustExceptionUtil {
    static ReqeustExceptionUtil mInstace;

    private ReqeustExceptionUtil() {
    }

    public static final ReqeustExceptionUtil getInstance() {
        if (mInstace == null) {
            synchronized (ReqeustExceptionUtil.class) {
                if (mInstace == null) mInstace = new ReqeustExceptionUtil();
            }
        }
        return mInstace;
    }

    /**
     * 处理错误信息
     *
     * @param e 错误信息
     */
    public void handle(Throwable e) {
        try {
            if (LogUtil.isDebug()) {
                if (e == null) {
                    LogUtil.e("Throwable is null !!!");
                } else {
                    LogUtil.e(MessageFormat.format("Throwable:{0}[{1}]", e.getClass().getName(), e.getMessage()));
                    e.printStackTrace();
                }
            }
            if (e instanceof IOException) {
                IOException((IOException) e);
            } else if (e instanceof NetStateException) {
                NetStateException((NetStateException) e);
            } else if (e instanceof MsgException) {
                MsgException((MsgException) e);
            } else {
                Exception(e);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /*IO异常*/
    private void IOException(IOException exception) {
        if (exception != null && exception.getMessage() != null && exception.getMessage().equals("Canceled")) {// 取消请求
            LogUtil.e("取消了一个请求");
        } else {//其他请求异常
            if (exception instanceof SocketException) {
                if (exception instanceof ConnectException) { //java.net.ConnectException: Failed to connect to /192.168.0.124:998
                    toast("请求服务器失败,请重试");
                } else { //网络请求失败
                    LogUtil.e("未捕获异常:" + exception.toString());
                    toast(exception.getMessage());
                }
            } else if (exception instanceof SocketTimeoutException) {
                toast("连接超时,可能是网络连接问题.");
            } else {
                LogUtil.e("未捕获异常:" + exception.toString());
                toast(exception.getMessage());
            }
        }
    }

    /*服务器回复结果状态异常*/
    private void NetStateException(NetStateException exception) { //网络状态异常
        try {
            int code = Integer.parseInt(exception.getMessage());
            switch (code) {
//            case 500:
//                break;
                default:
                    toast(MessageFormat.format("网络异常 [{0}]", code));
                    break;
            }
        } catch (Exception e) {
            toast(MessageFormat.format("网络异常 [{0}]", exception.getMessage()));
        }
    }

    /*异常消息*/
    private void MsgException(MsgException exception) {
        toast(exception.getMessage());
    }

    /*其他异常情况*/
    private void Exception(Throwable exception) { //其他异常
        toast(exception.getMessage());
    }

    /*Toast显示*/
    private void toast(String msg) {
        if (!TextUtils.isEmpty(msg)) ToastUtil.show(msg);
    }

}
