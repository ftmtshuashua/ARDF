package com.lfp.ardf.util;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * View 工具集合
 * Created by LiFuPing on 2018/5/18.
 */
public class ViewUtil {


    /**
     * 获得View的宽度和高度
     *
     * @param v
     * @param acion
     */
    public static void getViewWideHigh(final View v, final Action2<Integer, Integer> acion) {
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= 16)
                    v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                else v.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                acion.call(v.getWidth(), v.getHeight());
            }
        });
    }
    public static void getViewWideHigh2(final View v, final Action2<Integer, Integer> acion) {
        v.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                v.getViewTreeObserver().removeOnPreDrawListener(this);
                acion.call(v.getWidth(), v.getHeight());
                return false;
            }
        });

    }
    public static void getViewWideHigh3(final View v, final Action2<Integer, Integer> acion) {
        v.post(new Runnable() {
            @Override
            public void run() {
                acion.call(v.getWidth(), v.getHeight());
            }
        });
    }
    public static void getViewWideHigh4(final View v, final Action2<Integer, Integer> acion) {
        v.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                acion.call(v.getWidth(), v.getHeight());
            }
        });
    }
    public static void getViewWideHigh5(final View v, final Action2<Integer, Integer> acion){
        if (ViewCompat.isLaidOut(v)) {
            acion.call(v.getWidth(), v.getHeight());
        }
    }
    public interface Action2<A, B> {
        void call(A a, B b);
    }




    /**
     * 设置View显示状态
     */
    public static final void setVisibility(View view, int visibility) {
        if (view == null) return;
        if (view.getVisibility() != visibility) view.setVisibility(visibility);
    }

    /**
     * 设置ImageView的显示图片
     */
    public static final void setImageResource(ImageView view, int resid) {
        if (view == null) return;
        int key = 3 << 24;
        Object value = view.getTag(key);
        if (value != null && (int) value == resid) return;
        view.setImageResource(resid);
        view.setTag(key, resid);
    }

    /**
     * 设置ImageView的显示图片
     */
    public static final void setBackgroundResource(View view, int resid) {
        if (view == null) return;
        int key = 4 << 24;
        Object value = view.getTag(key);
        if (value != null && (int) value == resid) return;
        view.setBackgroundResource(resid);
        view.setTag(key, resid);
    }

    /**
     * 设置文本字体颜色
     */
    public static final void setTextColor(TextView view, int color) {
        if (view == null) return;
        int key = 5 << 24;
        Object value = view.getTag(key);
        if (value != null && (int) value == color) return;
        view.setTextColor(color);
        view.setTag(key, color);
    }

}
