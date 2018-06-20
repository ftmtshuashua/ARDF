package com.lfp.ardf.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.lfp.ardf.R;

/**
 * 布局阴影<br/>
 * 为任何布局的四周添加阴影效果<p>
 * Created by LiFuPing on 2018/6/15.
 */
public class ShadowView extends FrameLayout {
    /*表示左边需要阴影*/
    public static final int LEFT = 1;
    /*表示顶部需要阴影*/
    public static final int TOP = 2;
    /*表示右边需要阴影*/
    public static final int RIGHT = 4;
    /*表示底部需要阴影*/
    public static final int BOTTOM = 8;
    /*表示需要显示阴影*/
    public static final int FLAG_STYLE_MASK = 0xf;


    /*
    阴影样式
    LEFT|TOP|RIGHT|BOTTOM 分别表示4个方向是否有阴影
    */
    int shadow_style;
    /*阴影圆角半径*/
    float shadow_radius;
    /*阴影扩散距离*/
    float shadow_distance;
    /* x轴和y轴偏移量*/
    float shadow_dx;
    float shadow_dy;
    /*阴影颜色 , 必须有透明度才会生效*/
    int shadow_color;

    private Paint shadow_paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private RectF shadowRect = new RectF();//内容范围

    public ShadowView(Context context) {
        this(context, null);
    }

    public ShadowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        this.setWillNotDraw(false);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShadowView);
            shadow_distance = a.getDimensionPixelSize(R.styleable.ShadowView_shadow_distance, 0);
            shadow_radius = a.getDimensionPixelSize(R.styleable.ShadowView_shadow_radius, 0);
            shadow_dx = a.getDimensionPixelSize(R.styleable.ShadowView_shadow_dx, 0);
            shadow_dy = a.getDimensionPixelSize(R.styleable.ShadowView_shadow_dy, 0);
            shadow_style = a.getInt(R.styleable.ShadowView_shadow_style, 0);
            shadow_color = a.getColor(R.styleable.ShadowView_shadow_color, 0xCE2F66DE);
            a.recycle();
        }

        checkInit();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!hasShadow()) return;
        canvas.save();
        float size = shadow_distance * 1f;
        shadowRect.set(size, size, getWidth() - size, getHeight() - size);
        canvas.drawRoundRect(shadowRect, shadow_radius, shadow_radius, shadow_paint);
        canvas.restore();
    }

    /*初始化检查*/
    void checkInit() {
        if (!hasShadow()) return;
        float[] padding = new float[4];
        if (hasLeftShadow()) padding[0] = shadow_distance - shadow_dx;
        if (hasTopShadow()) padding[1] = shadow_distance - shadow_dy;
        if (hasRightShadow()) padding[2] = shadow_distance + shadow_dx;
        if (hasBottomShadow()) padding[3] = shadow_distance + shadow_dy;

        setPadding((int) padding[0], (int) padding[1], (int) padding[2], (int) padding[3]);

        shadow_paint.setColor(Color.TRANSPARENT);
        shadow_paint.setShadowLayer(shadow_distance * 0.6f, shadow_dx, shadow_dy, shadow_color);
    }

    /**
     * @return 是否有阴影
     */
    public boolean hasShadow() {
        return (shadow_style & FLAG_STYLE_MASK) != 0;
    }

    public boolean hasLeftShadow() {
        return (shadow_style & LEFT) != 0;
    }

    public boolean hasTopShadow() {
        return (shadow_style & TOP) != 0;
    }

    public boolean hasRightShadow() {
        return (shadow_style & RIGHT) != 0;
    }

    public boolean hasBottomShadow() {
        return (shadow_style & BOTTOM) != 0;
    }

}
