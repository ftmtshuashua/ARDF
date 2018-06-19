package com.lfp.androidrapiddevelopmentframework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 底部阴影<br/>
 * Created by LiFuPing on 2018/6/15.
 */
public class ShadowView extends FrameLayout {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF viewRect = new RectF();//内容范围

    private int radius = 45; /*圆角半径*/
    private int shadowColor = 0x882F66DE; //阴影颜色 - 一定要设置一个透明度
    private int distance; /*阴影距离*/

    public ShadowView(Context context) {
        this(context, null);
    }

    public ShadowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public ShadowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        distance = getPaddingBottom();

//        paint.setColor(Color.TRANSPARENT);
//        paint.setShadowLayer(distance * 0.5f, 0, distance * 0.45f, shadowColor);
        paint.setShadowLayer(distance, 0, 0, shadowColor);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        this.setWillNotDraw(false);

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        viewRect.set(distance * 0.6f, distance, getWidth() - distance * 0.6f, getHeight() - getPaddingRight() - distance);
    }


    Paint mPaint;

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPaint == null) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setStrokeWidth(1);
            mPaint.setStyle(Paint.Style.STROKE);
        }
        super.onDraw(canvas);

        canvas.save();
        /*范围*/
        mPaint.setColor(Color.RED);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);

        mPaint.setColor(Color.GREEN);
        /*阴影范围*/
        canvas.drawRect(viewRect, mPaint);

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(120);
        /*阴影*/
        canvas.drawText("啊哈", viewRect.left, viewRect.bottom, paint);
//        canvas.drawPaint(paint);
//        canvas.drawRoundRect(viewRect, radius, radius, paint);


        canvas.restore();
    }

}
