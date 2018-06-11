package com.lfp.ardf.debug;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.MessageFormat;
import java.util.logging.Logger;

import static com.lfp.ardf.util.Utils.checkNotNull;

/**
 * LogCat implementation for {@link LogAdapter}
 * <p>
 * This simply prints out all logs to Logcat by using standard {@link Log} class.
 */
public class LogcatLogStrategy implements LogAdapter {

    static final String DEFAULT_TAG = "NO_TAG";

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {
        checkNotNull(message);

        if (tag == null) {
            tag = DEFAULT_TAG;
        }
        StackTraceElement st = getMethCode();
        String str_meth = MessageFormat.format("{0}:{1,number,0}", st.getFileName(), st.getLineNumber());
        Log.println(priority, tag, MessageFormat.format("[{0}:{1}] - {2}", Thread.currentThread().getName(), str_meth, message));
    }

    private final StackTraceElement getMethCode() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        for (int i = PrettyFormatStrategy.MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement st = trace[i];
            if (st.isNativeMethod()) continue;
            String name = st.getClassName();
            if (name.equals(Thread.class.getName())) continue;
            if (name.equals(this.getClass().getName())) continue;
            if (!name.equals(LogUtil.class.getName()) && !name.equals(Logger.class.getName())) {
                return st;
            }
        }
        return null;
    }

}
