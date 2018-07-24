package com.lfp.ardf.util;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;

import com.lfp.ardf.model.NotProguard;

/**
 * <pre>
 * desc:
 *      颜色相关工具
 * function:
 *      rgb2hsv()       :RGB to HSV
 *      rgb2yuv()       :RGB to YUV
 *      yuv2rgb()       :YUV to RGB
 *      rgb2cmyk()      :RGB to CMYK
 *      cmyk2rgb()      :CMYK to RGB
 *      rgb2yiq()       :RGB to YIQ
 *      yiq2rgb()       :YIQ to RGB
 *      rgb2ycbcr()     :RGB to YCBCR
 *      ycbcr2rgb()     :YCBCR to RGB
 *      argb2Color()    :ARGB to Color
 *      isLight()       :判断颜色是否高亮
 *      setColorAlpha() :设置颜色透明度
 *      getRed()        :获得颜色Red值
 *      getGreen()      :获得颜色Green值
 *      getBlue()       :获得颜色Blue值
 *      getAlpha()      :获得颜色透明度
 *      getRandomColor():获得一个随机颜色
 *
 * Created by LiFuPing on 2018/7/2.
 * </pre>
 */
public class ColorUtils implements NotProguard {
    private ColorUtils() {

    }

    public static native int testAdd1(int color);

    /**
     * RGB 转 HSV
     *
     * @param R The red
     * @param G The green
     * @param B The Blue
     * @return <ul>
     * <li><code>hsv[0]</code> is Hue \([0..360[\)</li>
     * <li><code>hsv[1]</code> is Saturation \([0...1]\)</li>
     * <li><code>hsv[2]</code> is Value \([0...1]\)</li>
     * </ul>
     */
    public static float[] rgb2hsv(int R, int G, int B) {
        float[] hsv = new float[3];
        Color.RGBToHSV(R, G, B, hsv);
        return hsv;
    }

    /**
     * RGB 转 HSV
     *
     * @param color The color
     * @return <ul>
     * <li><code>hsv[0]</code> is Hue \([0..360[\)</li>
     * <li><code>hsv[1]</code> is Saturation \([0...1]\)</li>
     * <li><code>hsv[2]</code> is Value \([0...1]\)</li>
     * </ul>
     */
    public static float[] rgb2hsv(@ColorInt int color) {
        return rgb2hsv(getRed(color), getGreen(color), getBlue(color));
    }

    /**
     * RGB 转 YUV
     *
     * @param color The color
     * @return <ul>
     * <li><code>yuv[0]</code> is  \([0..255[\)</li>
     * <li><code>yuv[1]</code> is  \([-111...111]\)</li>
     * <li><code>yuv[2]</code> is  \([-156...156]\)</li>
     * </ul>
     */
    public static int[] rgb2yuv(@ColorInt int color) {
        return rgb2yuv(getRed(color), getGreen(color), getBlue(color));
    }

    /**
     * RGB 转 YUV
     *
     * @param R The red
     * @param G The green
     * @param B The Blue
     * @return <ul>
     * <li><code>yuv[0]</code> is  \([0..255[\)</li>
     * <li><code>yuv[1]</code> is  \([-111...111]\)</li>
     * <li><code>yuv[2]</code> is  \([-156...156]\)</li>
     * </ul>
     */
    public static int[] rgb2yuv(int R, int G, int B) {
        int yuv[] = new int[3];
        yuv[0] = (int) (0.299 * R + 0.587 * G + 0.114 * B); //76.245+149.685+29.07
        yuv[1] = (int) (-0.147 * R - 0.289 * G + 0.437 * B);
        yuv[2] = (int) (0.615 * R - 0.515 * G - 0.1 * B);
        return yuv;
    }

    /**
     * YUV 转 RGB
     *
     * @param y The y
     * @param u The u
     * @param v The v
     * @return The color
     */
    public static int yuv2rgb(int y, int u, int v) {

        int R = (int) (y + 1.14 * v);
        int G = (int) (y - 0.394 * u - 0.581 * v);
        int B = (int) (y + 2.028 * u);
        if (R < 0) R = 0;
        if (G < 0) G = 0;
        if (B < 0) B = 0;
        if (R > 255) R = 255;
        if (G > 255) G = 255;
        if (B > 255) B = 255;

        return argb2Color(R, G, B, 255);
    }

    /**
     * RGB 转 CMYK
     *
     * @param color The color
     * @return <ul>
     * <li><code>cmyk[0]</code> is  \([0..0[\)</li>
     * <li><code>cmyk[1]</code> is  \([0...0]\)</li>
     * <li><code>cmyk[2]</code> is  \([0...0]\)</li>
     * <li><code>cmyk[3]</code> is  \([0...0]\)</li>
     * </ul>
     */
    public static int[] rgb2cmyk(@ColorInt int color) {
        return rgb2cmyk(getRed(color), getGreen(color), getBlue(color));
    }

    /**
     * RGB 转 CMYK
     *
     * @param R The red
     * @param G The green
     * @param B The Blue
     * @return <ul>
     * <li><code>cmyk[0]</code> is  \([0..0[\)</li>
     * <li><code>cmyk[1]</code> is  \([0...0]\)</li>
     * <li><code>cmyk[2]</code> is  \([0...0]\)</li>
     * <li><code>cmyk[3]</code> is  \([0...0]\)</li>
     * </ul>
     */
    public static int[] rgb2cmyk(int R, int G, int B) {
        int cmyk[] = new int[4];
        cmyk[3] = (int) (Math.min(Math.min(255 - R, 255 - G), 255 - B) / 2.55);//cmykK
        int MyR = (int) (R / 2.55);
        int Div = 100 - cmyk[3];
        if (Div == 0) Div = 1;
        cmyk[0] = ((100 - MyR - cmyk[3]) / Div) * 100;//cmykC
        int MyG = (int) (G / 2.55);
        cmyk[1] = ((100 - MyG - cmyk[3]) / Div) * 100;
        int MyB = (int) (B / 2.55);
        cmyk[2] = ((100 - MyB - cmyk[3]) / Div) * 100;

        return cmyk;
    }

    /**
     * CMYK 转 RGB
     *
     * @param C The c
     * @param M The m
     * @param Y The y
     * @param K The k
     * @return The color
     */
    public static int cmyk2rgb(int C, int M, int Y, int K) {
        float MyC = C / 100;
        float MyM = M / 100;
        float MyY = Y / 100;
        float MyK = K / 100;

        int R = (int) ((1 - (MyC * (1 - MyK) + MyK)) * 255);
        int G = (int) ((1 - (MyM * (1 - MyK) + MyK)) * 255);
        int B = (int) ((1 - (MyY * (1 - MyK) + MyK)) * 255);

        if (R < 0) R = 0;
        if (G < 0) G = 0;
        if (B < 0) B = 0;
        if (R > 255) R = 255;
        if (G > 255) G = 255;
        if (B > 255) B = 255;

        return argb2Color(R, G, B, 255);
    }

    /**
     * RGB 转 YIQ
     *
     * @param color The color
     * @return <ul>
     * <li><code>yiq[0]</code> is  \([0..0[\)</li>
     * <li><code>yiq[1]</code> is  \([0...0]\)</li>
     * <li><code>yiq[2]</code> is  \([0...0]\)</li>
     * </ul>
     */
    public static int[] rgb2yiq(@ColorInt int color) {
        return rgb2yiq(getRed(color), getGreen(color), getBlue(color));
    }

    /**
     * RGB 转 YIQ
     *
     * @param R The red
     * @param G The green
     * @param B The Blue
     * @return <ul>
     * <li><code>yiq[0]</code> is  \([0..0[\)</li>
     * <li><code>yiq[1]</code> is  \([0...0]\)</li>
     * <li><code>yiq[2]</code> is  \([0...0]\)</li>
     * </ul>
     */
    public static int[] rgb2yiq(int R, int G, int B) {
        int yiq[] = new int[3];
        yiq[0] = (int) (0.299 * R + 0.587 * G + 0.114 * B);
        yiq[1] = (int) (0.596 * R - 0.274 * G - 0.322 * B);
        yiq[2] = (int) (0.212 * R - 0.523 * G + 0.311 * B);
        return yiq;
    }

    /**
     * YIQ 转 RGB
     *
     * @param y The y
     * @param i The i
     * @param q The q
     * @return The color
     */
    public static int yiq2rgb(float y, float i, float q) {

        int R = (int) (y + 0.956 * i + 0.621 * q);
        int G = (int) (y - 0.272 * i - 0.647 * q);
        int B = (int) (y - 1.105 * i + 1.702 * q);

        if (R < 0) R = 0;
        if (G < 0) G = 0;
        if (B < 0) B = 0;
        if (R > 255) R = 255;
        if (G > 255) G = 255;
        if (B > 255) B = 255;

        return argb2Color(R, G, B, 255);
    }

    /**
     * RGB 转 YCBCR
     *
     * @param color The color
     * @return <ul>
     * <li><code>ycbcr[0]</code> is  \([0..0[\)</li>
     * <li><code>ycbcr[1]</code> is  \([0...0]\)</li>
     * <li><code>ycbcr[2]</code> is  \([0...0]\)</li>
     * </ul>
     */
    public static int[] rgb2ycbcr(@ColorInt int color) {
        return rgb2ycbcr(getRed(color), getGreen(color), getBlue(color));
    }

    /**
     * RGB 转 YCBCR
     *
     * @param R The red
     * @param G The green
     * @param B The Blue
     * @return <ul>
     * <li><code>ycbcr[0]</code> is  \([0..0[\)</li>
     * <li><code>ycbcr[1]</code> is  \([0...0]\)</li>
     * <li><code>ycbcr[2]</code> is  \([0...0]\)</li>
     * </ul>
     */
    public static int[] rgb2ycbcr(int R, int G, int B) {
        int ycbcr[] = new int[3];
        ycbcr[0] = (int) (0.299 * R + 0.587 * G + 0.114 * B);
        ycbcr[1] = (int) (-0.1687 * R - 0.3313 * G + 0.5 * B + 128);
        if (ycbcr[1] > 255) ycbcr[1] = 255;
        ycbcr[2] = (int) (0.5 * R - 0.4187 * G - 0.0813 * B + 128);
        if (ycbcr[2] > 255) ycbcr[2] = 255;

        return ycbcr;
    }

    /**
     * YCBCR 转 RGB
     *
     * @param Y  The y
     * @param Cb The cb
     * @param Cr The cr
     * @return The color
     */
    public static int ycbcr2rgb(int Y, int Cb, int Cr) {
        int MyR = (int) (Y + 1.402 * (Cr - 128));
        int MyG = (int) (Y - 0.34414 * (Cb - 128) - 0.71414 * (Cr - 128));
        int MyB = (int) (Y + 1.772 * (Cb - 128));

        if (MyR > 255) MyR = 255;
        if (MyG > 255) MyG = 255;
        if (MyB > 255) MyB = 255;

        if (MyR < 0) MyR = 0;
        if (MyG < 0) MyG = 0;
        if (MyB < 0) MyB = 0;

        return argb2Color(MyR, MyG, MyB, 255);
    }

    /**
     * 通过 ARGB 获得color
     *
     * @param red   The red
     * @param green The green
     * @param blue  The blue
     * @param alpha The alpha
     * @return The color
     */
    public static int argb2Color(@IntRange(from = 0, to = 255) int red, @IntRange(from = 0, to = 255) int green, @IntRange(from = 0, to = 255) int blue, @IntRange(from = 0, to = 255) int alpha) {
        return red | green << 8 | blue << 16 | alpha << 24;
    }


    /**
     * 判断颜色是否为亮色
     *
     * @param color 颜色
     * @return {@code true} 亮色,{@code false}深色
     */
    public static boolean isLight(@ColorInt int color) {
        float value = getRed(color) * 0.299f + getGreen(color) * 0.587f + getBlue(color) * 0.114f;
        return value > 175;
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
     * 获得Color的Red值
     *
     * @param color The color
     * @return The red
     */
    public static int getRed(@ColorInt int color) {
        return ((color >> 16) & 0xff);
    }

    /**
     * 获得Color的Green值
     *
     * @param color The color
     * @return The green
     */
    public static int getGreen(@ColorInt int color) {
        return ((color >> 8) & 0xff);
    }

    /**
     * 获得Color的Blue值
     *
     * @param color The color
     * @return The blue
     */
    public static int getBlue(@ColorInt int color) {
        return ((color) & 0xff);
    }

    /**
     * 获得Color的Alpha值
     *
     * @param color The color
     * @return The alpha
     */
    public static int getAlpha(@ColorInt int color) {
        return ((color >> 24) & 0xff);
    }


    public static float getRedGray(@ColorInt int color) {
        return getRed(color) / 255.0f;
    }

    public static float getGreenGray(@ColorInt int color) {
        return getGreen(color) / 255.0f;
    }

    public static float getBlueGray(@ColorInt int color) {
        return getBlue(color) / 255.0f;
    }

    public static float getAlphaGray(@ColorInt int color) {
        return getAlpha(color) / 255.0f;
    }


    /**
     * 获得一个随机颜色
     *
     * @return Color
     */
    public static int getRandomColor() {
        return getRandomColor(1);
    }

    /**
     * 获得一个随机颜色值
     *
     * @param alpha 透明度
     * @return Color
     */
    public static int getRandomColor(@FloatRange(from = 0.0f, to = 1.0f) float alpha) {
        int color = 0;
        for (int i = 0; i < 3; i++) {
            int value = (int) (Math.random() * 255);
            int offset = i * 8;
            color |= (value << offset);
        }
        color |= ((int) (alpha * 255) << 24);
        return color;
    }

}
