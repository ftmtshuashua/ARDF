package com.lfp.ardf.debug;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.text.MessageFormat;

/**
 * 日志记录器
 */
public class LogUtil {
    private static String TAG = "LogUtil";

    private static final LogUtil logger = new LogUtil();

    private LogUtil() {
    }

    private static boolean isDebug = false;

    public void setTag(String tag) {
        TAG = tag;
    }

    public static void setDebug(boolean is) {
        isDebug = is;
    }

    /**
     * 初始化日志记录器，Debug包 - 日志开启 , Release包 - 日志关闭
     */
    public static void init(Context context) {
        try {
            setDebug((context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
        } catch (Exception e) {
            setDebug(false);
        }
    }

    public static boolean isDebug() {
        return isDebug;
    }

    /**
     * 获得打印日志所在方法名
     */
    private static String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) return null;
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) continue;
            if (st.getClassName().equals(Thread.class.getName())) continue;
            if (st.getClassName().equals(logger.getClass().getName())) continue;
            return "[ " + Thread.currentThread().getName() + ": " + st.getFileName() + ":" + st.getLineNumber() + ":" + st.getMethodName() + " ]";
        }
        return null;
    }


    public static void i(Object str) {
        if (!isDebug) return;
        String name = logger.getFunctionName();
        if (name != null) Log.i(TAG, MessageFormat.format("{0} - {1}", name, str));
        else Log.i(TAG, str.toString());
    }

    public static void v(Object str) {
        if (!isDebug) return;
        String name = logger.getFunctionName();
        if (name != null) Log.v(TAG, MessageFormat.format("{0} - {1}", name, str));
        else Log.v(TAG, str.toString());
    }

    public static void w(Object str) {
        if (!isDebug) return;
        String name = logger.getFunctionName();
        if (name != null) Log.w(TAG, MessageFormat.format("{0} - {1}", name, str));
        else Log.w(TAG, str.toString());
    }

    public static void e(Object str) {
        if (!isDebug) return;
        String name = logger.getFunctionName();
        if (name != null) Log.e(TAG, MessageFormat.format("{0} - {1}", name, str));
        else Log.e(TAG, str.toString());
    }

    public static void e(Exception ex) {
        if (!isDebug) return;
        String name = logger.getFunctionName();
        if (name != null) Log.e(TAG, "Exception:" + name, ex);
        else Log.e(TAG, "Exception:", ex);

    }

    public static void d(Object str) {
        if (!isDebug) return;
        String name = logger.getFunctionName();
        if (name != null) Log.d(TAG, MessageFormat.format("{0} - {1}", name, str));
        else Log.d(TAG, str.toString());
    }

    /**
     * 分段打印长度很长的日志
     */
    public static void longStr(String longStr) {
        int size = 4000;
        int index = 0;
        while ((longStr.length() - index) > 0) {
            int end = index + size;
            LogUtil.logger.e(longStr.substring(index, end < longStr.length() ? end : longStr.length()));
            index += size;
        }
    }


}
