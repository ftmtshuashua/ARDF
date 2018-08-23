package com.lfp.ardf.solution.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * <pre>
 * desc:
 *       View中包含一条时间线,通过观察时间线上时间的流逝可以做一些事情
 *
 *
 * function:
 *      addTimeEvent()      :添加事件
 *      deleteTimeEvent()   :删除事件
 *      deleteTimeEvents()  :删除所有事件
 *
 *
 * Created by LiFuPing on 2018/8/22.
 * </pre>
 */
public abstract class TimeLineView extends View {
    /*时间线*/
    AnimationTimeLine mAnimationTimeLine = new AnimationTimeLine();

    public TimeLineView(Context context) {
        super(context);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //如果View不可见或者时间线上没有观察者的时候停止之后的动作
        if (getVisibility() != View.VISIBLE || mAnimationTimeLine.getTimeObserverCount() == 0)
            return;
        mAnimationTimeLine.setContext(this, canvas);
        mAnimationTimeLine.elapse(getDrawingTime());
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    /**
     * 添加事件
     *
     * @param event 事件
     */
    public void addTimeEvent(TimeLineObserver event) {
        mAnimationTimeLine.addTimeObserver(event);
        invalidate();
    }

    /**
     * 删除事件
     *
     * @param event 事件
     */
    public void deleteTimeEvent(TimeLineObserver event) {
        mAnimationTimeLine.deleteTimeObserver(event);
    }

    /**
     * 删除所有事件
     */
    public void deleteTimeEvents() {
        mAnimationTimeLine.deleteTimeObservers();
    }

    /*  自定义动画时间线,提供Canvas回调 */
    private static final class AnimationTimeLine extends TimeLine {
        Canvas canvas;
        View mView;

        public void setContext(View v, Canvas canvas) {
            this.mView = v;
            this.canvas = canvas;
        }

        public Canvas getCanvas() {
            return canvas;
        }

        public View getView() {
            return mView;
        }
    }

    /**
     * <pre>
     *  动画效果绘制事件 , 在一段时间内绘制一段动画
     *
     * </pre>
     */
    public abstract static class AnimationDrawEvent extends TimeValueEvent {

        @Override
        public void onElapse(long actualtime, long runtime, long duration, float progress) {
            super.onElapse(actualtime, runtime, duration, progress);
            TimeLine timeLine = getTimeLine();
            if (timeLine instanceof AnimationTimeLine) {
                AnimationTimeLine timeline = (AnimationTimeLine) timeLine;
                onElapse(getValue(), timeline.getView(), timeline.getCanvas());
            }
        }

        /**
         * 时间流逝
         *
         * @param value the value
         * @param view the view
         * @param canvas the canvas
         */
        public abstract void onElapse(float value, View view, Canvas canvas);
    }

}
