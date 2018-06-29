package com.lfp.androidrapiddevelopmentframework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.lfp.androidrapiddevelopmentframework.widget.style.CoolWaitLoadingRenderer;
import com.lfp.ardf.widget.solution.BaseProgressBarView;

/**
 * 等待动画<br>
 * Created by LiFuPing on 2018/5/30.
 */
public class WaitProgressBar extends BaseProgressBarView {
    public WaitProgressBar(Context context) {
        this(context, null);
    }

    public WaitProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaitProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(color_border);

        mCoolWaitLoadingRenderer = new CoolWaitLoadingRenderer();

        setProgressDuration(2000);
    }

    int color_border = 0x88000000;
    Paint mPaint;
    RectF border_rectf;
    float radius_border;

    CoolWaitLoadingRenderer mCoolWaitLoadingRenderer;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (border_rectf == null) border_rectf = new RectF();
        border_rectf.set(0, 0, right - left, bottom - top);
        radius_border = border_rectf.width() * 0.1f;

        mCoolWaitLoadingRenderer.setCircleRadius(Math.min(border_rectf.width(), border_rectf.height()) / 3);
    }

    @Override
    protected void onDrawProgress(Canvas canvas, float scale) {
        int saveCount = canvas.save();
        canvas.drawRoundRect(border_rectf, radius_border, radius_border, mPaint);

        mCoolWaitLoadingRenderer.computeRender(scale);
        mCoolWaitLoadingRenderer.draw(canvas, border_rectf);
        canvas.restoreToCount(saveCount);
    }
}
