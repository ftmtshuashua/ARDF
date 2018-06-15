package com.lfp.ardf.module.net.util;

import com.lfp.ardf.exception.MsgException;
import com.lfp.ardf.exception.NetStateException;
import com.lfp.ardf.util.ToastUtil;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.MessageFormat;


/**
 * 错误信息提示工具
 * Created by LiFuPing on 2017/9/6.
 */
public class ExceptionTotalUtil {
    private ExceptionTotalUtil() {
    }

    /**
     * @param e 错误信息
     * @return 返回这个错误信息的显示信息
     */
    public static String getThrowableToastInfo(Throwable e) {
        if (e == null) return "This is null throwable!";
        if (e instanceof IOException) {
            /*SocketException: Socket closed*/
            if ("Socket closed".equals(e.getMessage()) || "Canceled".equals(e.getMessage())) {
                /*请求关闭 - 请求被取消*/
                return MessageFormat.format("请求被关闭({0})", e.getMessage());
            }
            if (e instanceof ConnectException) return "请求服务器失败,请重试!";
            if (e instanceof SocketTimeoutException) return "连接超时,可能是网络连接问题!";
            if (e instanceof InterruptedIOException) return e.getMessage();
            return e.getMessage();
        } else if (e instanceof NetStateException) {//网络状态异常
            return e.getMessage();
        } else if (e instanceof MsgException) {//消息异常
            return e.getMessage();
        }
        return e.getMessage();
    }

    /**
     * 通常情况下，网络请求终止等异常不应该被提示
     *
     * @param e 错误
     * @return 返回是否应该提示这个错误信息
     */
    public static boolean isShowThrowableToast(Throwable e) {
        if (e == null) return false;
        if (e instanceof IOException) {
            String msg = e.getMessage();
            if ("Socket closed".equals(msg) || "Canceled".equals(msg))
                return false;
            if (e instanceof InterruptedIOException) return false;
        }
        return true;
    }

    /**
     * 处理错误信息
     *
     * @param e 错误信息
     */
    public static void show(Throwable e) {
        try {
            e.printStackTrace();
            String excptin_info = getThrowableToastInfo(e);
            if (isShowThrowableToast(e)) ToastUtil.show(excptin_info);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

}
