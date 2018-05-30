package com.lfp.ardf.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

import com.lfp.ardf.util.SdkUtile;

/**
 * 继承该类，方便快捷的实现ProgressBar类识功能<br/>
 * <p>
 * View显示的时候执行动画
 * </p>
 * Created by LiFuPing on 2018/5/30.
 */
public abstract class BaseProgressBarView extends View {

    private Transformation mTransformation;
    private AlphaAnimation mAnimation;

    public BaseProgressBarView(Context context) {
        this(context, null);
    }

    public BaseProgressBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseProgressBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTransformation = new Transformation();

        mAnimation = new AlphaAnimation(0.0f, 1.0f);
        mAnimation.setRepeatMode(AlphaAnimation.RESTART);
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setDuration(300);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setStartTime(Animation.START_ON_FIRST_FRAME);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getVisibility() != View.VISIBLE) return;
        mAnimation.getTransformation(getDrawingTime(), mTransformation);
        final float scale = mTransformation.getAlpha();

        onDrawAnimation(canvas, scale);

        if (SdkUtile.has(16)) {
            postInvalidateOnAnimation();
        } else invalidate();
    }


    protected abstract void onDrawAnimation(Canvas canvas,float scale);

    public void setInterpolator(Interpolator i){
        mAnimation.setInterpolator(i);
    }
    public void setDuration(long durationMillis){
        mAnimation.setDuration(durationMillis);
    }

}
