package com.lfp.ardf.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.lfp.ardf.debug.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.Manifest.permission.EXPAND_STATUS_BAR;

/**
 * <pre>
 * desc:
 *      状态栏相关工具类
 * function:
 *      setStatusBarImmersiveView()             :设置状态栏沉浸式View
 *      getStatusBarHeight()                    :获得状态栏高度
 *      setStatusBarVisibility()                :设置状态栏可见度
 *      isStatusBarVisible()                    :获得状态栏可见性
 *      setStatusBarLightMode()                 :设置状态栏高亮显示 6.0以上有效
 *      setViewMarginTopEqualStatusBarHeight()  :给View顶部增加一个大小为BarHeight的margin值
 *      setViewPaddingTopEqualStatusBarHeight() :给View顶部增加一个大小为BarHeight的Padding值
 *      setViewPaddingBottomEqualNavigationBarHeight() :
 *      setStatusBarMode()                      :设置状态栏模式(改变状态栏文本颜色)
 *      setStatusBarColor()                     :设置状态栏颜色
 *      getActionBarHeight()                    :获得系统ActionBar高度
 *      setNotificationBarVisibility()          :显示/隐藏通知栏
 *      getNavigationBarHeight():                      :获得导航栏高度
 *      setNavigationBarVisibility();                  :显示/隐藏导航栏
 *      isNavigationBarVisible();                      :获得导航栏可见性
 *      setNavigationBarImmersive();                   :沉浸式导航栏
 *      setNavigationBarColor();                       ;设置导航栏颜色
 *      getNavigationBarColor():                       :获得导航栏颜色
 *      hasNavigationBar()                              :判断导航栏是否显示
 * Created by LiFuPing on 2017/9/6.
 * </pre>
 */
public class BarUtils {
    private static final String VIEW_TAG_COLOR = "VIEW_TAG_COLOR";
    private static final int TAG_OFFSET_VIEW_MARGIN = -973542;
    private static final int TAG_OFFSET_VIEW_PADDING_STATUS_BAR = -973543;
    private static final int TAG_OFFSET_VIEW_PADDING_NAVIGATION_BAR = -973544;

    /* StatusBar 相关函数 */

    /**
     * 获得状态栏高度
     *
     * @return 状态栏高度
     */
    public static int getStatusBarHeight() {
        Resources resources = AppUtils.getResources();
        int resid = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resid);
    }

    /**
     * 设置状态栏可见度
     *
     * @param activity  The activity.
     * @param isVisible {@true}显示状态栏 {@false}隐藏状态栏
     */
    public static void setStatusBarVisibility(@NonNull final Activity activity, final boolean isVisible) {
        setStatusBarVisibility(activity.getWindow(), isVisible);
    }

    /**
     * 设置状态栏可见度
     *
     * @param window    The window.
     * @param isVisible {@code true}:显示状态栏 {@code false}:隐藏状态栏
     */
    public static void setStatusBarVisibility(@NonNull final Window window, final boolean isVisible) {
        if (isVisible) window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        else window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 获得状态栏可见度
     *
     * @param activity The activity.
     * @return {@code true}:可见 {@code false}: 不可见
     */
    public static boolean isStatusBarVisible(@NonNull final Activity activity) {
        return isStatusBarVisible(activity.getWindow());
    }

    /**
     * 获得状态栏可见度
     *
     * @param window The window.
     * @return {@code true}:可见 {@code false}: 不可见
     */
    public static boolean isStatusBarVisible(@NonNull final Window window) {
        return (window.getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0;
    }

    /**
     * 设置状态栏高亮模式
     *
     * @param activity    The activity.
     * @param isLightMode {@code true}:高亮模式(该模式下状态栏字体颜色为深色)  {@code false} 清除状态栏深色字体颜色
     */
    public static void setStatusBarLightMode(@NonNull final Activity activity, final boolean isLightMode) {
        setStatusBarLightMode(activity.getWindow(), isLightMode);
    }

    /**
     * 设置状态栏高亮模式
     *
     * @param window      The window.
     * @param isLightMode {@code true}:高亮模式(该模式下状态栏字体颜色为深色)  {@code false} 清除状态栏深色字体颜色
     */
    public static void setStatusBarLightMode(@NonNull final Window window, final boolean isLightMode) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View decorView = window.getDecorView();
                if (decorView != null) {
                    int vis = decorView.getSystemUiVisibility();
                    if (isLightMode) {
//                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    } else {
                        vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    }
                    decorView.setSystemUiVisibility(vis);
                }
            } else if (PhoneUtils.getRom() == PhoneUtils.ROM.MIUI) {
                Class clazz = window.getClass();
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (isLightMode) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
            } else if (PhoneUtils.getRom() == PhoneUtils.ROM.Flyme) {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (isLightMode) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
            }
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    /**
     * 增加View的MarginTop的高度,大小为状态栏高度大小.
     * 此方法通常用来匹配透明状态栏效果
     *
     * @param view     需要控制View
     * @param addition {@code true}增加高度 ,{@code false}如果已经增加了高度,则去掉增加的高度
     */
    public static void setViewMarginTopEqualStatusBarHeight(@NonNull View view, boolean addition) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        Object haveSetOffset = view.getTag(TAG_OFFSET_VIEW_MARGIN);

        if (addition && (haveSetOffset != null && (Boolean) haveSetOffset)) return;
        if (!addition && (haveSetOffset == null || !(Boolean) haveSetOffset)) return;

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(
                layoutParams.leftMargin,
                addition ? (layoutParams.topMargin + getStatusBarHeight()) : (layoutParams.topMargin - getStatusBarHeight()),
                layoutParams.rightMargin,
                layoutParams.bottomMargin);

        view.setLayoutParams(layoutParams);
        view.setTag(TAG_OFFSET_VIEW_MARGIN, addition);
    }

    /**
     * 增加View的PaddingTop的高度,大小为状态栏高度大小.
     * 此方法通常用来匹配透明状态栏效果
     *
     * @param view     需要控制View
     * @param addition {@code true}增加高度 ,{@code false}如果已经增加了高度,则去掉增加的高度
     */
    public static void setViewPaddingTopEqualStatusBarHeight(@NonNull View view, boolean addition) {
        setViewPaddingTopEqualStatusBarHeight(view, addition, true);
    }

    /**
     * 增加View的PaddingTop的高度,大小为状态栏高度大小.
     * 此方法通常用来匹配透明状态栏效果
     *
     * @param view       需要控制View
     * @param addition   {@code true}增加高度 ,{@code false}如果已经增加了高度,则去掉增加的高度
     * @param autoHeight {@code true}如果View的高度是固定的,则高度也是随着Padding变化
     */
    public static void setViewPaddingTopEqualStatusBarHeight(@NonNull View view, boolean addition, boolean autoHeight) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        Object haveSetOffset = view.getTag(TAG_OFFSET_VIEW_PADDING_STATUS_BAR);

        if (addition && (haveSetOffset != null && (Boolean) haveSetOffset)) return;
        if (!addition && (haveSetOffset == null || !(Boolean) haveSetOffset)) return;

        int statusBarHeight = getStatusBarHeight();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (autoHeight && layoutParams.height >= 0) {
            layoutParams.height = addition ? (layoutParams.height + statusBarHeight) : (layoutParams.height - statusBarHeight);
        }
        view.setPadding(
                view.getPaddingLeft(),
                addition ? (view.getPaddingTop() + statusBarHeight) : (view.getPaddingTop() - statusBarHeight),
                view.getPaddingRight(),
                view.getPaddingBottom()
        );

        view.setTag(TAG_OFFSET_VIEW_PADDING_STATUS_BAR, addition);
    }

    /**
     * 增加View的PaddingTop的高度,大小为导航栏高度大小.
     * 此方法通常用来匹配透明导航栏效果
     *
     * @param view     需要控制View
     * @param addition {@code true}增加高度 ,{@code false}如果已经增加了高度,则去掉增加的高度
     */
    public static void setViewPaddingBottomEqualNavigationBarHeight(@NonNull View view, boolean addition) {
        setViewPaddingBottomEqualNavigationBarHeight(view, addition, true);
    }

    /**
     * 增加View的PaddingTop的高度,大小为导航栏高度大小.
     * 此方法通常用来匹配透明导航栏效果
     *
     * @param view       需要控制View
     * @param addition   {@code true}增加高度 ,{@code false}如果已经增加了高度,则去掉增加的高度
     * @param autoHeight {@code true}如果View的高度是固定的,则高度也是随着Padding变化
     */
    public static void setViewPaddingBottomEqualNavigationBarHeight(@NonNull View view, boolean addition, boolean autoHeight) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        Object haveSetOffset = view.getTag(TAG_OFFSET_VIEW_PADDING_NAVIGATION_BAR);

        if (addition && (haveSetOffset != null && (Boolean) haveSetOffset)) return;
        if (!addition && (haveSetOffset == null || !(Boolean) haveSetOffset)) return;

        int navBarHeight = getNavigationBarHeight();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (autoHeight && layoutParams.height >= 0) {
            layoutParams.height = addition ? (layoutParams.height + navBarHeight) : (layoutParams.height - navBarHeight);
        }
        view.setPadding(
                view.getPaddingLeft(),
                view.getPaddingTop(),
                view.getPaddingRight(),
                addition ? (view.getPaddingBottom() + navBarHeight) : (view.getPaddingBottom() - navBarHeight)
        );

        view.setTag(TAG_OFFSET_VIEW_PADDING_NAVIGATION_BAR, addition);
    }

    /**
     * 全屏模式下，为一个布局的顶部增加状态栏高度的内边距，使得布局内容位于状态栏的下方
     *
     * @param v 需要调整的布局
     */
    public static void setStatusBarImmersiveView(@NonNull final View v) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        setTransparentStatusBar(Utils.getActivityByView(v).getWindow(), true);
        setViewPaddingTopEqualStatusBarHeight(v, true);
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity The activity.
     * @param color    The status bar's color.
     */
    public static void setStatusBarColor(@NonNull final Activity activity, @ColorInt final int color) {
        setStatusBarColor(activity, color, true);
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity     The activity.
     * @param autoTxtColor 自动状态栏文本颜色,文本根据背景色自动在白色与黑色之间切换
     * @param color        The status bar's color.
     */
    public static void setStatusBarColor(@NonNull final Activity activity, @ColorInt final int color, boolean autoTxtColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTransparentStatusBar(activity.getWindow(), true);
            ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView();
            View fakeStatusBarView = parent.findViewWithTag(VIEW_TAG_COLOR);
            if (fakeStatusBarView == null) {
                View statusBarView = new View(activity);
                statusBarView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
                statusBarView.setBackgroundColor(color);
                statusBarView.setTag(VIEW_TAG_COLOR);
                parent.addView(statusBarView);
            } else {
                ViewUtils.setVisibility(fakeStatusBarView, View.VISIBLE);
                fakeStatusBarView.setBackgroundColor(color);
            }
        } else autoTxtColor = false;

        if (autoTxtColor) setStatusBarLightMode(activity, ColorUtils.isLight(color));
    }

    /* 设置透明状态栏 */
    private static void setTransparentStatusBar(@NonNull final Window window, boolean isTransparent) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        if (isTransparent) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
        }
    }


    /* ActionBar 相关函数*/

    /**
     * 获得系统ActionBar的高度
     *
     * @return the action bar's height
     */
    public static int getActionBarHeight() {
        TypedValue tv = new TypedValue();
        if (AppUtils.getApp().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, AppUtils.getResources().getDisplayMetrics());
        }
        return 0;
    }

    /* NotificationBar 相关函数*/

    /**
     * 设置通知栏显示状态
     * <p>Must hold
     * {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR" />}</p>
     * <p>部分机型需要
     * {@code <uses-permission android:name="android.permission.INTERACT_ACROSS_USERS_FULL" />}</p>
     *
     * @param isVisible True to set notification bar visible, false otherwise.
     */
    @RequiresPermission(EXPAND_STATUS_BAR)
    public static void setNotificationBarVisibility(final boolean isVisible) {
        String methodName;
        if (isVisible) {
            methodName = (Build.VERSION.SDK_INT <= 16) ? "expand" : "expandNotificationsPanel";
        } else {
            methodName = (Build.VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
        }

        //invokePanels
        try {
            @SuppressLint("WrongConstant")
            Object service = AppUtils.getApp().getSystemService("statusbar");
            @SuppressLint("PrivateApi")
            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod(methodName);
            expand.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* NavigationBar */

    /**
     * 获得导航栏的高度
     *
     * @return the navigation bar's height
     */
    public static int getNavigationBarHeight() {
        Resources res = AppUtils.getApp().getResources();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId != 0) return res.getDimensionPixelSize(resourceId);
        return 0;
    }

    /**
     * 设置导航栏可见性
     *
     * @param activity  The activity.
     * @param isVisible True to set notification bar visible, false otherwise.
     */
    public static void setNavigationBarVisibility(@NonNull final Activity activity, boolean isVisible) {
        setNavigationBarVisibility(activity.getWindow(), isVisible);
    }

    /**
     * 设置导航栏可见性
     *
     * @param window    The window.
     * @param isVisible True to set notification bar visible, false otherwise.
     */
    public static void setNavigationBarVisibility(@NonNull final Window window, boolean isVisible) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        int flag = window.getDecorView().getSystemUiVisibility();
        int flag_hidden = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        if (isVisible) flag &= ~flag_hidden;
        else flag |= flag_hidden;
        window.getDecorView().setSystemUiVisibility(flag);
    }

    /**
     * 判断导航栏是否可见性
     *
     * @return {@code true}: 可见{@code false}: 不可见
     */
    public static boolean isNavigationBarVisible() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            WindowManager windowManager = (WindowManager) AppUtils.getApp().getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(AppUtils.getApp()).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }

//        boolean isNoLimits = (window.getAttributes().flags & WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) != 0;
//        if (isNoLimits) return false;
//        int visibility = window.getDecorView().getSystemUiVisibility();
//        return (visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
    }

    /**
     * 沉浸式导航栏
     *
     * @param activity The activity.
     */
    public static void setNavigationBarImmersive(@NonNull final Activity activity) {
        setNavigationBarImmersive(activity.getWindow());
    }

    /**
     * 沉浸式导航栏
     *
     * @param window The window.
     */
    public static void setNavigationBarImmersive(@NonNull final Window window) {
        setTransparentNavigationBar(window, true);
    }

    /**
     * 设置导航栏颜色
     *
     * @param activity The activity.
     * @param color    The navigation bar's color.
     */
    public static void setNavigationBarColor(@NonNull final Activity activity, @ColorInt final int color) {
        setNavigationBarColor(activity.getWindow(), color);
    }

    /**
     * 设置导航栏颜色
     *
     * @param window The window.
     * @param color  The navigation bar's color.
     */
    public static void setNavigationBarColor(@NonNull final Window window, @ColorInt final int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(color);
        }
    }

    /**
     * 获得导航栏颜色
     *
     * @param activity The activity.
     * @return the color of navigation bar
     */
    public static int getNavigationBarColor(@NonNull final Activity activity) {
        return getNavigationBarColor(activity.getWindow());
    }

    /**
     * 获得导航栏颜色
     *
     * @param window The window.
     * @return the color of navigation bar
     */
    public static int getNavigationBarColor(@NonNull final Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return window.getNavigationBarColor();
        }
        return 0;
    }

    /* 设置透明导航栏*/
    private static void setTransparentNavigationBar(@NonNull final Window window, boolean isTransparent) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        if (isTransparent) window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        else window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

}
