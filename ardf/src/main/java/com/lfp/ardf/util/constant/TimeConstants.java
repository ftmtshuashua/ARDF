package com.lfp.ardf.util.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 * desc:
 *
 * function:
 *
 * Created by LiFuPing on 2018/6/27.
 * </pre>
 */
public final class TimeConstants {

    /**毫秒*/
    public static final int MSEC = 1;
    /**秒*/
    public static final int SEC = 1000;
    /**分*/
    public static final int MIN = 60000;
    /**时*/
    public static final int HOUR = 3600000;
    /**天*/
    public static final int DAY = 86400000;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
