package com.lfp.ardf.util;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;

import com.lfp.ardf.model.NotProguard;

/**
 * <pre>
 * desc:
 *      颜色相关工具
 * function:
 *
 *
 * Created by LiFuPing on 2018/7/2.
 * </pre>
 */
public class ColorUtils implements NotProguard {
    private ColorUtils() {

    }

    /**
     * 设置颜色透明度
     *
     * @param color 原色
     * @param alpha 透明度
     * @return 透明之后的颜色
     */
    public static int setColorAlpha(@ColorInt final int color, @IntRange(from = 0, to = 255) final int alpha) {
        if (alpha == 0) return color;
        float a = 1 - alpha / 255f;
        int red = (color >> 16) & 0xff;
        int green = (color >> 8) & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return Color.argb(255, red, green, blue);
    }

    /**
     * RGB 转 HSV
     *
     * @param color color rgb
     * @return color hsv
     */
    public static float[] rgb2hsv(@ColorInt int color) {
        float[] hsv = new float[3];
        Color.RGBToHSV(getRed(color), getGreen(color), getBlue(color), hsv);
        return hsv;
    }

    public static native int testAdd1(int color);

    public static boolean isLightColor(@ColorInt int color) {
        return false;
    }


    public static int getRed(@ColorInt int color) {
        return ((color >> 16) & 0xff);
    }

    public static int getGreen(@ColorInt int color) {
        return ((color >> 8) & 0xff);
    }

    public static int getBlue(@ColorInt int color) {
        return ((color) & 0xff);
    }

    public static int getAlpha(@ColorInt int color) {
        return ((color >> 24) & 0xff);
    }

    public static float getRed2Hex(@ColorInt int color) {
        return getRed(color) / 255.0f;
    }

    public static float getGreen2Hex(@ColorInt int color) {
        return getGreen(color) / 255.0f;
    }

    public static float getBlue2Hex(@ColorInt int color) {
        return getBlue(color) / 255.0f;
    }

    public static float getAlpha2Hex(@ColorInt int color) {
        return getAlpha(color) / 255.0f;
    }

}
