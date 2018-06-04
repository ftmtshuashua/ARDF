package com.lfp.androidrapiddevelopmentframework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.ardf.widget.BaseProgressBarView;


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
        mPaint = new Paint();

        Color_Progress = getResources().getColor(R.color.colorPrimaryDark);
        Color_Animation = 0xffF5F5F5;
    }

    int Color_BG = 0x88eeeeee; /*背景色*/
    int Color_Progress; /*进度条颜色*/
    int Color_Animation; /*动画颜色*/

    Paint mPaint;
    int mProgress;
    int mMaxProgress = 100;


    public void setMaxProgress(int progress) {
        mMaxProgress = progress;
    }

    public void setProgress(int progress) {
        if (progress < 10) progress = 10;
        if (progress > 100) progress = 100;
        mProgress = progress;
    }

    public int getProgress() {
        return mProgress;
    }

    @Override
    protected void onDrawAnimation(Canvas canvas, float scale) {
        final int saveCount = canvas.save();
        canvas.drawColor(Color_BG);

        int right = getWidth() * getProgress() / mMaxProgress;

        mPaint.setColor(Color_Progress);
        canvas.drawRect(0, 0, right, getHeight(), mPaint);

        right *= scale;
        mPaint.setColor(Color_Animation);
        canvas.drawRect(right * 0.7f, 0, right, getHeight(), mPaint);

        canvas.restoreToCount(saveCount);
    }
}
