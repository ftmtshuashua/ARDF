package com.lfp.ardf.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

import java.util.LinkedList;

/**
 * 继承该类，方便快捷的实现ProgressBar效果<br>
 * <p>
 * - 当View能被看见, onDrawProgress()方法会被自动回调.<br>
 * - 当View隐藏的时候停止回调onDrawProgress()<br>
 * - 回调间隔大约为16ms
 * </p>
 * Created by LiFuPing on 2018/5/30.
 */
public abstract class BaseProgressBarView extends View {
    ProgressAnimation mAnimation;
    LinkedList<ProgressAnimation> mCustomEvent = new LinkedList<>();

    public BaseProgressBarView(Context context) {
        this(context, null);
    }

    public BaseProgressBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseProgressBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mAnimation = new ProgressAnimation(0.0f, 1.0f);
        mAnimation.setRepeatMode(AlphaAnimation.RESTART);
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setDuration(300);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setStartTime(Animation.START_ON_FIRST_FRAME);
    }

    OnAnimationEnd mOnAnimationEnd = new OnAnimationEnd() {
        @Override
        public void onAnimationEnd(ProgressAnimation animation) {
            mCustomEvent.remove(animation);
        }
    };


    /**
     * @param animation
     */
    public void put(ProgressAnimation animation) {
        if (animation == null) return;
        animation.setOnAnimationEnd(mOnAnimationEnd);
        mCustomEvent.add(animation);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getVisibility() != View.VISIBLE) return;
        if (!mAnimation.transformation(this)) return;
        if (mCustomEvent != null) {
            for (int i = 0; i < mCustomEvent.size(); i++) {
                ProgressAnimation animation = mCustomEvent.get(i);
                animation.transformation(this);
            }
        }
        onDrawProgress(canvas, mAnimation.getScale());
        ViewCompat.postInvalidateOnAnimation(this);
    }

    /**
     * 当View可见的时候回调方法，子类在该方法中根据返回的scale值计算动画状态
     *
     * @param canvas 画布
     * @param scale  取值范围 0.0 ~ 1.0,它的效果可以通过getAnimation()所获得对象的属性变化
     */
    protected abstract void onDrawProgress(Canvas canvas, @FloatRange(from = 0.0, to = 1.0) float scale);

    /**
     * @param durationMillis 设置进度从 0.0 到 1.0 所需要的总时间
     */
    public void setProgressDuration(long durationMillis) {
        mAnimation.setDuration(durationMillis);
    }

    /**
     * 获得进度动画的配置属性
     * <p>
     * 修改这个配置,可以实现不同的效果
     *
     * @return ProgressAnimation
     */
    public ProgressAnimation getProgressAnimation() {
        return mAnimation;
    }


    /**
     * 进度动画,在正确的事件返回正确
     */
    protected static class ProgressAnimation extends AlphaAnimation implements Animation.AnimationListener {
        Transformation mTransformation;
        AnimationListener mListener;
        OnAnimationEnd mAnimationEndListener;

        public ProgressAnimation(float fromAlpha, float toAlpha) {
            super(fromAlpha, toAlpha);
            this.mTransformation = new Transformation();
            super.setAnimationListener(this);
        }

        /*计算*/
        private boolean transformation(View v) {
            return getTransformation(v.getDrawingTime(), mTransformation);
        }

        public float getScale() {
            return mTransformation.getAlpha();
        }

        @Override
        public void setAnimationListener(AnimationListener listener) {
            mListener = listener;
        }

        private void setOnAnimationEnd(OnAnimationEnd listener) {
            mAnimationEndListener = listener;
        }

        @Override
        public void onAnimationStart(Animation animation) {
            if (mListener != null) mListener.onAnimationStart(animation);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (mListener != null) mListener.onAnimationEnd(animation);
            if (mAnimationEndListener != null) mAnimationEndListener.onAnimationEnd(this);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            if (mListener != null) mListener.onAnimationRepeat(animation);
        }
    }

    /*动画结束*/
    private interface OnAnimationEnd {
        void onAnimationEnd(ProgressAnimation animation);
    }


}
