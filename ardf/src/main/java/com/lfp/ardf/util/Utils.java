package com.lfp.ardf.util;

import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;


/**
 * 常用工具方法集合 <br/>
 * Created by LiFuPing on 2018/5/30.
 */
public class Utils {

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(CharSequence str) {
        return TextUtils.isEmpty(str);
    }

    /**
     * 如果这个对象为空则返回true
     *
     * @param obj 被检测的对象
     * @return 是否为空对象
     */
    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    /**
     * 如果这个对象为空这抛出 {@code NullPointerException},否则返回这个对象
     *
     * @param obj 被检测的对象
     * @return 如果对象不为空则返回这个对象
     */
    public static <T> T checkNotNull(@Nullable final T obj) {
        if (obj == null) throw new NullPointerException();
        return obj;
    }

    /**
     *  如果这个对象为空这抛出 {@code NullPointerException},否则返回这个对象
     * @param obj 被检测的对象
     * @param msg 对象为空时候显示的消息
     * @return 如果对象不为空则返回这个对象
     */
    public static <T> T checkNotNull(@Nullable final T obj,String msg){
        if (obj == null) throw new NullPointerException(msg);
        return obj;
    }
    /**
     * Returns true if a and b are equal, including if they are both null.
     * <p><i>Note: In platform versions 1.1 and earlier, this method only worked well if
     * both the arguments were instances of String.</i></p>
     *
     * @param a first CharSequence to check
     * @param b second CharSequence to check
     * @return true if a and b are equal
     * <p>
     * NOTE: Logic slightly change due to strict policy on CI -
     * "Inner assignments should be avoided"
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        if (a != null && b != null) {
            int length = a.length();
            if (length == b.length()) {
                if (a instanceof String && b instanceof String) {
                    return a.equals(b);
                } else {
                    for (int i = 0; i < length; i++) {
                        if (a.charAt(i) != b.charAt(i)) return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return 如果当前线程是主线程则返回true
     */
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

}
