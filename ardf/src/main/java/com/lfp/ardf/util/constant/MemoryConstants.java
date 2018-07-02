package com.lfp.ardf.util.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 * desc:
 *      内存相关常量
 * function:
 *      BYTE    :字节
 *      KB      :1 kb = 1024 byte
 *      MB      :1 kb = 1048576 byte
 *      GB      :1 kb = 1073741824 byte
 *
 * Created by LiFuPing on 2018/6/27.
 * </pre>
 */
public final class MemoryConstants {

    public static final int BYTE = 1;
    public static final int KB   = 1024;
    public static final int MB   = 1048576;
    public static final int GB   = 1073741824;

    @IntDef({BYTE, KB, MB, GB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
