package com.lfp.ardf.widget.solution;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * <pre>
 * desc:
 *      轻松实现View动画
 *
 * function:
 *
 * Created by LiFuPing on 2018/7/24.
 * </pre>
 */
public abstract class BaseAnimationView extends View {


    public BaseAnimationView(Context context) {
        super(context);
        init();
    }

    public BaseAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    void init() {

    }




}
