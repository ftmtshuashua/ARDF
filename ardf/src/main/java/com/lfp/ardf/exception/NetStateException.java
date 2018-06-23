package com.lfp.ardf.exception;

/**
 * 网络状态异常
 * 如果一个请求回复不在 200 &lt;= code &lt; 300 内当时候 抛出该异常,并且说明异常信息,
 * 通常这个异常信息用于提示用户
 * Created by LiFuPing on 2017/9/5.
 */

public class NetStateException extends Exception {

    public NetStateException(String msg) {
        super(msg);
    }



}
