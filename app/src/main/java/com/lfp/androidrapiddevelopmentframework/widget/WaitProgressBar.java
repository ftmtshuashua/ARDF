package com.lfp.androidrapiddevelopmentframework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lfp.androidrapiddevelopmentframework.widget.style.CoolWaitLoadingRenderer;
import com.lfp.ardf.solution.animation.TimeLineView;
import com.lfp.ardf.solution.animation.TimeValueEvent;

/**
 * <pre>
 * desc:
 *      菊花动画
 *
 * function:
 *
 * Created by LiFuPing on 2018/8/22.
 * </pre>
 */
public class WaitProgressBar extends TimeLineView {
    /*圆角矩形背景颜色*/
    int color_border = 0x88000000;
    Paint mPaint;
    /*View范围*/
    RectF border_rectf;
    /*圆角角度*/
    float radius_border;

    /*动画实现 感谢 https://github.com/dinuscxj/LoadingDrawable*/
    CoolWaitLoadingRenderer mCoolWaitLoadingRenderer;

    public WaitProgressBar(Context context) {
        this(context, null);
    }

    public WaitProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaitProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    TimeValueEvent animation;

    void init() {
        mCoolWaitLoadingRenderer = new CoolWaitLoadingRenderer();
        if (Build.VERSION.SDK_INT <= 19) setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mPaint = new Paint();
        mPaint.setColor(color_border);

        animation = new TimeValueEvent();
        animation.setDuration(2000);
        animation.setRepeatCount(AnimationDrawEvent.INFINITE);
        animation.setRepeatMode(AnimationDrawEvent.RESTART);
        addTimeEvent(animation);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (border_rectf == null) border_rectf = new RectF();
        border_rectf.set(0, 0, right - left, bottom - top);
        radius_border = border_rectf.width() * 0.1f;

        mCoolWaitLoadingRenderer.setCircleRadius(Math.min(border_rectf.width(), border_rectf.height()) / 3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int saveCount = canvas.save();
        canvas.drawRoundRect(border_rectf, radius_border, radius_border, mPaint);

        mCoolWaitLoadingRenderer.computeRender(animation.getValue());
        mCoolWaitLoadingRenderer.draw(canvas, border_rectf);

        canvas.restoreToCount(saveCount);
    }


}
