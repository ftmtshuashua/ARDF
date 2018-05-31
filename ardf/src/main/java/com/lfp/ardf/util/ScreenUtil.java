package com.lfp.ardf.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.text.MessageFormat;

/**
 * 屏幕测绘工具
 * Created by LiFuPing on 2017/9/29.
 */

public class ScreenUtil {
    private static Context mContext;

    private ScreenUtil() {

    }

    public static void init(Context c) {
        mContext = c.getApplicationContext();
    }

    /**
     * PX 转 DP
     */
    public static int px2dip(float value) {
        final float sale = mContext.getResources().getDisplayMetrics().density;
        return (int) ((value - 0.5f) / sale);
    }

    /**
     * DP 转 PX
     */
    public static int dip2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, mContext.getResources().getDisplayMetrics());
    }

    /**
     * SP 转 PX
     */
    public static int sp2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, mContext.getResources().getDisplayMetrics());
    }

    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕的高度
     *
     * @return
     */
    public static int getScreenHeight() {
        return mContext.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获得屏幕信息
     */
    public static String getScreenInfo() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return MessageFormat.format("Screen_Info\n" +
                        "size:{0,number,0.##}*{1,number,0.##}\n" +
                        "density:{2,number,0.##}\n" +
                        "densityDpi:{3,number,0.##}\n" +
                        "scaledDensity:{4,number,0.##}\n" +
                        "xdpi:{5,number,0.##}\n" +
                        "ydpi:{6,number,0.##}"
                , displayMetrics.widthPixels, displayMetrics.heightPixels
                , displayMetrics.density
                , displayMetrics.densityDpi
                , displayMetrics.scaledDensity
                , displayMetrics.xdpi
                , displayMetrics.ydpi


        );
    }
}
