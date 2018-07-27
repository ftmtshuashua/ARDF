package com.lfp.androidrapiddevelopmentframework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.ardf.widget.solution.animation.SimperAttachmentListener;
import com.lfp.ardf.widget.solution.animation.TimelineAttachment;
import com.lfp.ardf.widget.solution.animation.TimelineView;


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
public class WebProgressBar extends TimelineView {

    int Color_BG; /*背景色*/
    int Color_Progress; /*进度条颜色*/
    int Color_Animation; /*动画颜色*/
    Paint mPaint;

    int mProgress;
    int mMaxProgress = 100;

    /*进度条停留位置*/
    float Width_ProgressBar_Animation = 0;
    /*上一次设置时进度条宽度*/
    float Width_ProgressBar_Last = 0;
    /*实时进度条宽度*/
    float Width_ProgressBar_New = 0;

    /*进度平滑国度动画数据*/
    TimelineAttachment ProgressTransition;

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
        Color_Progress = getResources().getColor(R.color.colorPrimaryDark);
        Color_Animation = 0xffF5F5F5;


        TimelineAttachment attachment = obtain();
        attachment.setDuration(1000);
        attachment.setRepeatCount(TimelineAttachment.REPEAT_COUNT_INFINITE);
        attachment.setAttachmentListener(mProgressListener);
        attach(attachment);

        ProgressTransition = new TimelineAttachment(0.0f, 1.0f);
        ProgressTransition.setDuration(300);
        ProgressTransition.setAttachmentListener(mProgressTransitionListener);
    }


    public void setMaxProgress(int progress) {
        mMaxProgress = progress;
    }

    public void setProgress(int progress) {
        if (progress < 10) progress = 10;
        if (progress > mMaxProgress) progress = mMaxProgress;
        mProgress = progress;

        if (ProgressTransition.isAttach()) { /*上一个过度动画还未完成,需要停止它再重新开始*/
            detach(ProgressTransition);
        }
        Width_ProgressBar_New = getWidth() * mProgress / (float) mMaxProgress;

        attach(ProgressTransition);
    }

    public int getProgress() {
        return mProgress;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int saveCount = canvas.save();
        canvas.drawColor(Color_BG);

        float scale = mProgressTransitionListener.getValue();
        /*绘制进度条动画*/
        mPaint.setColor(Color_Progress);
        if (ProgressTransition.isAttach()) {
            Width_ProgressBar_Animation = (Width_ProgressBar_New - Width_ProgressBar_Last) * scale + Width_ProgressBar_Last;
        } else {
            Width_ProgressBar_Animation = Width_ProgressBar_New;
        }
        canvas.drawRect(0, 0, Width_ProgressBar_Animation, getHeight(), mPaint);

        /*绘制动态进度效果*/
        mPaint.setColor(Color_Animation);
        scale = mProgressListener.getValue();
        float animationWight = (float) (Math.sin(scale * Math.PI) * Width_ProgressBar_Animation / 2);
        canvas.translate((Width_ProgressBar_Animation - animationWight) * scale, 0);
        canvas.drawRect(0, 2, animationWight, getHeight() - 2, mPaint);

        canvas.restoreToCount(saveCount);
    }

    /*进度监听*/
    SimperAttachmentListener mProgressListener = new SimperAttachmentListener();
    SimperAttachmentListener mProgressTransitionListener = new SimperAttachmentListener() {

        public void onAttachmentStart(TimelineAttachment animation) {
        }


        @Override
        public void onAttachmentRepeat(TimelineAttachment animation) {
        }

        @Override
        public void onAttachmentEnd(TimelineAttachment animation) {
            Width_ProgressBar_Last = Width_ProgressBar_Animation;
        }
    };


    private static final class ProgressDynamicEffect {

    }


}
