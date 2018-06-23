package com.lfp.ardf.debug;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * <br>
 * Created by LiFuPing on 2018/5/30.
 */
public interface LogAdapter {
    void log(int priority, @Nullable String tag, @NonNull String message);
}
