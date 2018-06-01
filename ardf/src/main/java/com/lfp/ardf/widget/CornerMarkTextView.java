package com.lfp.ardf.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 角标TextView :在TextView的表面显示一些信息<br/>
 * 使用详细参见Demo<br/>
 * Created by LiFuPing on 2018/6/1.
 */
public class CornerMarkTextView extends AppCompatTextView {
    public CornerMarkTextView(Context context) {
        super(context);
    }

    public CornerMarkTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CornerMarkTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 设置角标偏移量
     *
     * @param messageOffsetX
     * @param messageOffsetY
     */
    protected void setCornerMarkOffset(float messageOffsetX, float messageOffsetY) {

    }

}
