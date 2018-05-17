package com.lfp.ardf.exception;

/**
 * 消息异常
 * Created by LiFuPing on 2017/9/5.
 */

public class MsgException extends RuntimeException {
    public MsgException(String msg) {
        super(msg);
    }
}
