package com.lfp.androidrapiddevelopmentframework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.ardf.solution.animation.TimeEvent;
import com.lfp.ardf.solution.animation.TimeLineView;
import com.lfp.ardf.solution.animation.TimeValueEvent;

/**
 * <pre>
 * desc:
 *      web进度条
 *
 * function:
 *
 * Created by LiFuPing on 2018/6/4.
 * </pre>
 */
public class WebProgressBar extends TimeLineView {
    int Color_BG; /*背景色*/
    int Color_Progress; /*进度条颜色*/
    int Color_Animation; /*滚动动画颜色*/
    Paint mPaint;

    /*进度相关数据*/
    int mProgress;
    int mMaxProgress = 100;
    float save_progress_width; //上一次保存的进度的宽度
    float middle_progress_width; //动画实时进度,当前一次动画执行未结束的时候,新发起一个动画事件,让两次动画事件能平顺过度

    public WebProgressBar(Context context) {
        super(context);
        init();
    }

    public WebProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WebProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Color_BG = 0x88eeeeee;
        Color_Animation = 0xffF5F5F5;
        Color_Progress = getResources().getColor(R.color.colorPrimaryDark);

        mAnimation.setDuration(1000);
        mAnimation.setRepeatCount(TimeEvent.INFINITE);
        addTimeEventInDrawAfter(mAnimation);
    }

    /**
     * 设置最大进度
     *
     * @param max 最大值
     */
    public void setMaxProgress(int max) {
        mMaxProgress = max;
    }

    /**
     * 设置进度
     *
     * @param progress 进度值
     */
    public void setProgress(final int progress) {
        mProgressAnimation.detach();
        mProgressAnimation.clear();
        mProgress = progress;
        mProgressAnimation.setDuration(300);
        addTimeEventInDrawBefore(mProgressAnimation);
    }

    /**
     * 获得当前进度值
     *
     * @return 进度值
     */
    public int getProgress() {
        return mProgress;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color_BG);

        /*绘制进度*/
        mPaint.setColor(Color_Progress);
        final int width = getWidth();
        final int height = getHeight();
        final float animation_rate = mProgressAnimation.getValue();
        final float progress_with = width * (mProgress / (float) mMaxProgress);
        middle_progress_width = (progress_with - save_progress_width) * animation_rate + save_progress_width;
        canvas.drawRect(0, 0, middle_progress_width, height, mPaint);
    }


    //平滑的进度动画
    TimeValueEvent mProgressAnimation = new TimeValueEvent(0.0f, 1.0f) {

        @Override
        protected void onDetach() {
            super.onDetach();
            save_progress_width = middle_progress_width;
        }
    };

    //滚动动画
    TimeEvent mAnimation = new AnimationDrawEvent() {

        @Override
        public void onElapse(float value, View view, Canvas canvas) {
            final int saveCount = canvas.save();
            mPaint.setColor(Color_Animation);
            float animationWight = (float) (Math.sin(value * Math.PI) * middle_progress_width / 2);
            canvas.translate((middle_progress_width - animationWight) * value, 0);
            canvas.drawRect(0, 2, animationWight, view.getHeight() - 2, mPaint);
            canvas.restoreToCount(saveCount);
        }
    };
}
