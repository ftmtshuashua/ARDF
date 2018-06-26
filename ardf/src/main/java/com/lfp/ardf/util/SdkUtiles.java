package com.lfp.ardf.util;

import android.os.Build;

/**
 * SDK版本查询工具
 */
public class SdkUtiles {

    /**
     * 这个方法判断当前SDK版本 是否大于等于传入的版本等级 <br>
     *
     * @param lv leve
     * @return boolean
     */
    public static boolean has(int lv) {
        return Build.VERSION.SDK_INT >= lv;
    }

    /**
     * 获取当前SDK的版本号
     * @return int
     */
    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }


    /**
     * Magic version number for a current development build, which has
     * not yet turned into an official release.
     */
    public static final int CUR_DEVELOPMENT = 10000;

    /**
     * October 2008: The original, first, version of Android.  Yay!
     */
    public static final int BASE = 1;

    /**
     * February 2009: First Android update, officially called 1.1.
     */
    public static final int BASE_1_1 = 2;

    /**
     * May 2009: Android 1.5.
     */
    public static final int CUPCAKE = 3;

    /**
     * September 2009: Android 1.6.
     */
    public static final int DONUT = 4;

    /**
     * November 2009: Android 2.0
     */
    public static final int ECLAIR = 5;

    /**
     * December 2009: Android 2.0.1
     */
    public static final int ECLAIR_0_1 = 6;

    /**
     * January 2010: Android 2.1
     */
    public static final int ECLAIR_MR1 = 7;

    /**
     * June 2010: Android 2.2
     */
    public static final int FROYO = 8;

    /**
     * November 2010: Android 2.3
     */
    public static final int GINGERBREAD = 9;

    /**
     * February 2011: Android 2.3.3.
     */
    public static final int GINGERBREAD_MR1 = 10;

    /**
     * February 2011: Android 3.0.
     */
    public static final int HONEYCOMB = 11;

    /**
     * May 2011: Android 3.1.
     */
    public static final int HONEYCOMB_MR1 = 12;

    /**
     * June 2011: Android 3.2.
     */
    public static final int HONEYCOMB_MR2 = 13;

    /**
     * October 2011: Android 4.0.
     */
    public static final int ICE_CREAM_SANDWICH = 14;

    /**
     * December 2011: Android 4.0.3.
     */
    public static final int ICE_CREAM_SANDWICH_MR1 = 15;

    /**
     * June 2012: Android 4.1.
     */
    public static final int JELLY_BEAN = 16;

    /**
     * Android 4.2: Moar jelly beans!
     */
    public static final int JELLY_BEAN_MR1 = 17;

    /**
     * Android 4.3: Jelly Bean MR2, the revenge of the beans.
     */
    public static final int JELLY_BEAN_MR2 = 18;

    /**
     * Android 4.4: KitKat, another tasty treat.
     */
    public static final int KITKAT = 19;

    /**
     * Android 4.4W: KitKat for watches, snacks on the run.
     */
    public static final int KITKAT_WATCH = 20;

    /**
     * Lollipop. A flat one with beautiful shadows. But still tasty.  Android 5.0
     */
    public static final int LOLLIPOP = 21;

    /**
     * Lollipop with an extra sugar coating on the outside!  Android 5.1
     */
    public static final int LOLLIPOP_MR1 = 22;

    /**
     * M is for Marshmallow! Android 6.0
     */
    public static final int M = 23;

    /**
     * N is for ¯\_(ツ)_/¯. Android 7.0
     */
    public static final int N = 24;

    /**
     * @return 获得SDK版本名称
     */
    public static String getSdkVersionName() {
        switch (getSdkVersion()) {
            case BASE:
                return "Android_1.0";
            case BASE_1_1:
                return "Android_1.1";
            case CUPCAKE:
                return "Android_1.5";
            case DONUT:
                return "Android_1.6";
            case ECLAIR:
                return "Android_2.0";
            case ECLAIR_0_1:
                return "Android_2.0.1";
            case ECLAIR_MR1:
                return "Android_2.1";
            case FROYO:
                return "Android_2.2";
            case GINGERBREAD:
                return "Android_2.3";
            case GINGERBREAD_MR1:
                return "Android_2.3.3";
            case HONEYCOMB:
                return "Android_3.0";
            case HONEYCOMB_MR1:
                return "Android_3.1";
            case HONEYCOMB_MR2:
                return "Android_3.2";
            case ICE_CREAM_SANDWICH:
                return "Android_4.0";
            case ICE_CREAM_SANDWICH_MR1:
                return "Android_4.0.3";
            case JELLY_BEAN:
                return "Android_4.1";
            case JELLY_BEAN_MR1:
                return "Android_4.2";
            case JELLY_BEAN_MR2:
                return "Android_4.3";
            case KITKAT:
                return "Android_4.4";
            case KITKAT_WATCH:
                return "Android_4.4W";
            case LOLLIPOP:
                return "Android_5.0";
            case LOLLIPOP_MR1:
                return "Android_5.1";
            case M:
                return "Android_6.0";
            case N:
                return "Android_7.0";
        }
        return "unknown";
    }
}
