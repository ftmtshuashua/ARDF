package com.lfp.ardf.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;

/**
 * 将drawable元素与文本剧中显示,暂时只支持一张图<br/>
 * Created by LiFuPing on 2018/6/1.
 */
public class DrawableCenterTextView extends CornerMarkTextView {

    public DrawableCenterTextView(Context context) {
        this(context, null);
    }

    public DrawableCenterTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableCenterTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    Drawable[] mDrawables;

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        if (mDrawables != null) {
            final int LEFT = 0;
            final int TOP = 1;
            final int RIGHT = 2;
            final int BOTTOM = 3;
            Drawable drawableLeft = mDrawables[LEFT];
            if (drawableLeft != null) {
                setGravity(Gravity.CENTER_VERTICAL);
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = drawableLeft.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
                setCornerMarkOffset((getWidth() - bodyWidth) / 2, 0);
            }

            Drawable drawableTop = mDrawables[TOP];
            if (drawableTop != null) {
                setGravity(Gravity.CENTER_HORIZONTAL);
                float textHeight = getPaint().getTextSize();
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = drawableTop.getIntrinsicHeight();
                float bodyHeight = textHeight + drawableWidth + drawablePadding;
                canvas.translate(0, (getHeight() - bodyHeight) / 2);
                setCornerMarkOffset(0, (getHeight() - bodyHeight) / 2);
            }

            Drawable drawableRight = mDrawables[RIGHT];
            if (drawableRight != null) {
                setGravity(Gravity.CENTER_VERTICAL);
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = drawableRight.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                setPadding(0, 0, (int) (getWidth() - bodyWidth), 0);
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
                setCornerMarkOffset((getWidth() - bodyWidth) / 2, 0);
            }

            Drawable drawableDottom = mDrawables[BOTTOM];
            if (drawableDottom != null) {
                setGravity(Gravity.CENTER_HORIZONTAL);
                float textHeight = getPaint().getTextSize();
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = drawableDottom.getIntrinsicHeight();
                float bodyHeight = textHeight + drawableWidth + drawablePadding;
                setPadding(0, 0, 0, (int) (getWidth() - bodyHeight) / 2);
                canvas.translate(0, (getHeight() - bodyHeight) / 2);
                setCornerMarkOffset(0, (getHeight() - bodyHeight) / 2);
            }

        }
        super.onDraw(canvas);
        canvas.save();
    }


    @Override
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        super.setCompoundDrawables(left, top, right, bottom);
        mDrawables = getCompoundDrawables();
    }


}
