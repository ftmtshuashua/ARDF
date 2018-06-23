package com.lfp.ardf.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * 继承该类，方便快捷的实现ProgressBar类识功能<br>
 * <p>
 * - 当View能被看见,onDrawAnimation()方法会被自动回调.<br>
 * - 当View隐藏的时候停止回调onDrawAnimation()<br>
 * - 回调间隔大约为16ms
 * </p>
 * Created by LiFuPing on 2018/5/30.
 */
public abstract class BaseProgressBarView extends View {

    /*一次新的Scale获取事件*/
    private static final int FLAG_NEW_SCALE_EVENT = 1;

    private Transformation mTransformation;
    private AlphaAnimation mAnimation;

    int flag;

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
        boolean isRuning = mAnimation.getTransformation(getDrawingTime(), mTransformation);
        if (!isRuning) return;
        float scale = mTransformation.getAlpha();
        onDrawAnimation(canvas, scale);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    /**
     * 当View可见的时候回调方法，子类在该方法中根据返回的scale值计算动画状态
     *
     * @param canvas 画布
     * @param scale  取值范围 0.0 ~ 1.0,它的效果可以通过getAnimation()所获得对象的属性变化
     */
    protected abstract void onDrawAnimation(Canvas canvas, float scale);

    /**
     * 调用此方法之后,将会生成一个新的从0 到 1 的事件,并回调onScaleEvent方法
     */
    public void startOnceNewScale() {

    }

    protected void onScaleEvent() {

    }

    /**
     * @param durationMillis 设置进度从 0.0 到 1.0 所需要的总时间
     */
    public void setAnimationDuration(long durationMillis) {
        mAnimation.setDuration(durationMillis);
    }

    /**
     * @return 获得进度计算工具，通过设置AlphaAnimation的属性灵活改变返回进度的值
     */
    public AlphaAnimation getAnimation() {
        return mAnimation;
    }

}
