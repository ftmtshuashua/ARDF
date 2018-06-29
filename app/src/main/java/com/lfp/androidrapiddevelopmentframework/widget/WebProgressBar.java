package com.lfp.androidrapiddevelopmentframework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.ardf.widget.solution.BaseProgressBarView;


/**
 * web进度条
 * Created by LiFuPing on 2018/6/4.
 */
public class WebProgressBar extends BaseProgressBarView {
    public WebProgressBar(Context context) {
        this(context, null);
    }

    public WebProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Color_Progress = getResources().getColor(R.color.colorPrimaryDark);
        Color_Animation = 0xffF5F5F5;

        setProgressDuration(1000);
    }

    int Color_BG = 0x88eeeeee; /*背景色*/
    int Color_Progress; /*进度条颜色*/
    int Color_Animation; /*动画颜色*/

    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int mProgress;
    int mMaxProgress = 100;

    ProgressAnimation mProgressAnimation; /*均匀的进度提升效果*/

    public void setMaxProgress(int progress) {
        mMaxProgress = progress;
    }

    public void setProgress(int progress) {
        if (progress < 10) progress = 10;
        if (progress > mMaxProgress) progress = mMaxProgress;
        mProgress = progress;


        if (mProgressAnimation == null) {
            mProgressAnimation = new ProgressAnimation(0.0f, 1.0f);
        } else {
            mProgressAnimation.cancel();
            mProgressAnimation.reset();
        }
        mProgressAnimation.setRepeatMode(AlphaAnimation.RESTART);
        mProgressAnimation.setRepeatCount(1);
        mProgressAnimation.setDuration(500);
        mProgressAnimation.setInterpolator(new LinearInterpolator());
        mProgressAnimation.setStartTime(Animation.START_ON_FIRST_FRAME);
        put(mProgressAnimation);
    }


    public int getProgress() {
        return mProgress;
    }


    float dynamic_progress_width; /*动态进度*/

    @Override
    protected void onDrawProgress(Canvas canvas, float scale) {
        final int saveCount = canvas.save();
        canvas.drawColor(Color_BG);

        float real_progress_width = getWidth() * mProgress / (float) mMaxProgress;
        if (mProgressAnimation == null) {
            dynamic_progress_width = real_progress_width;
        } else {
            dynamic_progress_width = (real_progress_width - dynamic_progress_width) * mProgressAnimation.getScale() + dynamic_progress_width;
        }

        mPaint.setColor(Color_Progress);
        canvas.drawRect(0, 0, dynamic_progress_width, getHeight(), mPaint);


        float animationWight = (float) (Math.sin(scale * Math.PI) * dynamic_progress_width / 2);
        canvas.translate((dynamic_progress_width - animationWight) * scale, 0);
        mPaint.setColor(Color_Animation);
        canvas.drawRect(0, 0, animationWight, getHeight(), mPaint);

        canvas.restoreToCount(saveCount);
    }


}
