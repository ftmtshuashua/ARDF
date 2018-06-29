package com.lfp.ardf.util;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

import java.text.MessageFormat;

import static android.Manifest.permission.WRITE_SETTINGS;

/**
 * <pre>
 * desc:
 *      屏幕工具
 * function:
 *      px2dip()            :px转dp
 *      dip2px()            :dp转px
 *      sp2px()             :sp转px
 *      applyDimension()    :屏幕相关数据转换
 *      getScreenWidth()    :获得屏幕宽度
 *      getScreenHeight()   :获得屏幕高度
 *      getScreenInfo()     :获得屏幕信息简要
 *      setFullScreen()     :设置全屏
 *      setLandscape()      :设置横屏
 *      setPortrait()       :设置竖屏
 *      isLandscape()       :判断是否为横屏
 *      isPortrait()        :判断是否为竖屏
 *      getScreenRotation() :获得屏幕旋转角度
 *      screenShot()        :截屏
 *      isScreenLock()      :返回屏幕是否锁定
 *      setSleepDuration()  :设定锁屏时间
 *      getSleepDuration()  :获得锁屏时间
 *
 * Created by LiFuPing on 2017/9/29.
 * </pre>
 */
public class ScreenUtils {
    private ScreenUtils() {
    }

    /**
     * PX 转 DP
     *
     * @param px px
     * @return int
     */
    public static int px2dip(float px) {
        final float sale = AppUtils.getResources().getDisplayMetrics().density;
        return (int) ((px - 0.5f) / sale);
    }

    /**
     * DP 转 PX
     *
     * @param dp dp
     * @return int
     */
    public static int dip2px(float dp) {
        return (int) applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, AppUtils.getResources().getDisplayMetrics());
    }

    /**
     * SP 转 PX
     *
     * @param sp sp
     * @return int
     */
    public static int sp2px(float sp) {
        return (int) applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, AppUtils.getResources().getDisplayMetrics());
    }

    /**
     * PX 转 SP
     *
     * @param px sp
     * @return int
     */
    public static int px2sp(final float px) {
        final float fontScale = AppUtils.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }

    /**
     * 获得屏幕的宽度
     *
     * @return int
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) AppUtils.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return AppUtils.getResources().getDisplayMetrics().widthPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * 获得屏幕的高度
     *
     * @return int
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) AppUtils.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return AppUtils.getResources().getDisplayMetrics().heightPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * 获得屏幕信息
     *
     * @return String
     */
    public static String getScreenInfo() {
        DisplayMetrics displayMetrics = AppUtils.getResources().getDisplayMetrics();
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


    /**
     * Converts an unpacked complex data value holding a dimension to its final floating
     * point value. The two parameters <var>unit</var> and <var>value</var>
     * are as in {@link TypedValue#TYPE_DIMENSION}.
     *
     * @param unit    The unit to convert from.
     * @param value   The value to apply the unit to.
     * @param metrics Current display metrics to use in the conversion --
     *                supplies display density and scaling information.
     * @return The complex floating point value multiplied by the appropriate
     * metrics depending on its unit.
     */
    public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        return TypedValue.applyDimension(unit, value, AppUtils.getResources().getDisplayMetrics());
    }

    /**
     * Set full screen.
     *
     * @param activity The activity.
     */
    public static void setFullScreen(@NonNull final Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * Set the screen to landscape.
     *
     * @param activity The activity.
     */
    public static void setLandscape(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * Set the screen to portrait.
     *
     * @param activity The activity.
     */
    public static void setPortrait(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * Return whether screen is landscape.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLandscape() {
        return AppUtils.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * Return whether screen is portrait.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isPortrait() {
        return AppUtils.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * Return the rotation of screen.
     *
     * @param activity The activity.
     * @return the rotation of screen
     */
    public static int getScreenRotation(@NonNull final Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            default:
                return 0;
        }
    }


    /**
     * Return the bitmap of screen.
     *
     * @param activity The activity.
     * @return the bitmap of screen
     */
    public static Bitmap screenShot(@NonNull final Activity activity) {
        return screenShot(activity, false);
    }

    /**
     * Return the bitmap of screen.
     *
     * @param activity          The activity.
     * @param isDeleteStatusBar True to delete status bar, false otherwise.
     * @return the bitmap of screen
     */
    public static Bitmap screenShot(@NonNull final Activity activity, boolean isDeleteStatusBar) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap bmp = decorView.getDrawingCache();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret;
        if (isDeleteStatusBar) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = resources.getDimensionPixelSize(resourceId);
            ret = Bitmap.createBitmap(
                    bmp,
                    0,
                    statusBarHeight,
                    dm.widthPixels,
                    dm.heightPixels - statusBarHeight
            );
        } else {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }
        decorView.destroyDrawingCache();
        return ret;
    }

    /**
     * Return whether screen is locked.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isScreenLock() {
        KeyguardManager km =
                (KeyguardManager) AppUtils.getApp().getSystemService(Context.KEYGUARD_SERVICE);
        return km != null && km.inKeyguardRestrictedInputMode();
    }

    /**
     * Set the duration of sleep.
     * <p>Must hold {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     *
     * @param duration The duration.
     */
    @RequiresPermission(WRITE_SETTINGS)
    public static void setSleepDuration(final int duration) {
        Settings.System.putInt(
                AppUtils.getApp().getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT,
                duration
        );
    }

    /**
     * Return the duration of sleep.
     *
     * @return the duration of sleep.
     */
    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(
                    AppUtils.getApp().getContentResolver(),
                    Settings.System.SCREEN_OFF_TIMEOUT
            );
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -123;
        }
    }

}
