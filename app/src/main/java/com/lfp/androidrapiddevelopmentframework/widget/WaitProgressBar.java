package com.lfp.androidrapiddevelopmentframework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.widget.BaseProgressBarView;

import java.text.MessageFormat;

/**
 * 等待动画<br/>
 * Created by LiFuPing on 2018/5/30.
 */
public class WaitProgressBar extends BaseProgressBarView {
    public WaitProgressBar(Context context) {
        this(context ,null);
    }

    public WaitProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaitProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
    }

    Paint mPaint;

    @Override
    protected void onDrawAnimation(Canvas canvas, float scale) {
        canvas.drawColor(Color.BLACK);
        canvas.drawText(String.valueOf(scale), getWidth()/2, getHeight()/2, mPaint);
        LogUtil.e(MessageFormat.format("进度比例:{0,number,0.##}", scale));
    }
}
