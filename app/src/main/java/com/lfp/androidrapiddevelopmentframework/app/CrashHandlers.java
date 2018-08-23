package com.lfp.androidrapiddevelopmentframework.app;

import android.content.Context;

import com.lfp.ardf.debug.LogUtil;


/**
 * <pre>
 * desc:
 *      未捕获异常处理
 *
 * function:
 *
 * Created by admin on 2018/8/20.
 * </pre>
 */
public class CrashHandlers implements Thread.UncaughtExceptionHandler {
    private CrashHandlers() {
    }


    private static CrashHandlers instance;

    public synchronized static CrashHandlers getInstance() {
        if (instance == null) {
            instance = new CrashHandlers();
        }
        return instance;
    }


    public void init(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (LogUtil.isDebug()) ex.printStackTrace();
    }
}
