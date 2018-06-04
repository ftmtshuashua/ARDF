package com.lfp.ardf.exception;

import android.database.sqlite.SQLiteException;

/**
 * 未捕获异常处理<br/>
 * Created by LiFuPing on 2018/6/4.
 */
public class UncaughtException implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!(e instanceof SQLiteException)) {
            throw new RuntimeException(e);
        }
    }
}
