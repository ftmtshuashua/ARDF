package com.lfp.androidrapiddevelopmentframework.demo.visualeffect.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import com.lfp.ardf.solution.animation.TimeLineView;
import com.lfp.ardf.solution.animation.TimeValueEvent;
import com.lfp.ardf.util.AssetsDirUtils;

import java.util.HashMap;

/**
 * <pre>
 * desc:
 *      打飞机游戏
 *
 * function:
 *
 * Created by LiFuPing on 2018/7/26.
 * </pre>
 */
public class AircraftGameView extends TimeLineView {

    private static final int SCENE_MENY = 0;
    private static final int SCENE_LEVE_1 = 1;

    public AircraftGameView(Context context) {
        super(context);
        init();
    }

    public AircraftGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AircraftGameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mSceneArray.put(SCENE_MENY, new MenuScene(this));
        mSceneArray.put(SCENE_LEVE_1, new Leve_1(this));

        switchScene(SCENE_MENY);
    }

    Paint mPaint;
    Scene mCurrentScene; //当前游戏场景

    HashMap<Integer, Scene> mSceneArray = new HashMap<>();


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurrentScene != null) {
            mCurrentScene.onDraw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mCurrentScene != null) {
            if (mCurrentScene.onTouchEvent(event)) return true;
        }
        return super.onTouchEvent(event);
    }

    public void onPause(){

    }

    public   void onDestroy(){

    }

    /*切换场景*/
    private void switchScene(int scenename) {
        Scene scene = mSceneArray.get(scenename);
        if (mCurrentScene == null) {
            mCurrentScene = scene;
        } else { /*场景切换动画*/
            mCurrentScene.onQuit();
            mCurrentScene = scene;
        }
        scene.onEnter();
        invalidate();
    }

    /* 场景 */
    private static abstract class Scene {
        TimeLineView mView;

        public Scene(TimeLineView v) {
            mView = v;
        }

        protected abstract void onDraw(Canvas canvas);

        protected boolean onTouchEvent(MotionEvent event) {
            return false;
        }

        public int getWidth() {
            return mView.getWidth();
        }

        public int getHeight() {
            return mView.getHeight();
        }

        protected void switchScene(int scene) {
            ((AircraftGameView) mView).switchScene(scene);
        }

        public TimeLineView getContext() {
            return mView;
        }

        /*当进入场景的时候*/
        protected void onEnter() {

        }

        /*当退出场景的时候*/
        protected void onQuit() {

        }
    }

    /* 场景 - 菜单 */
    private static final class MenuScene extends Scene {

        Paint mPaint;

        public MenuScene(TimeLineView v) {
            super(v);
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(Color.BLACK);
            mPaint.setTextSize(30);
        }

        @Override
        public void onDraw(Canvas canvas) {
            canvas.drawColor(Color.YELLOW);

            canvas.drawText("打飞机", getWidth() / 2, getHeight() / 4, mPaint);

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN://进入游戏场景
                    switchScene(SCENE_LEVE_1);
                    break;
            }
            return super.onTouchEvent(event);
        }
    }

    /* 场景 - 设置 */

    /* 场景 - 关卡 */
    private static final class Leve_1 extends Scene {
        Paint mPaint;
        Bitmap mBackgroupBitmap;

        public Leve_1(TimeLineView v) {
            super(v);
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


        }

        @Override
        protected void onEnter() {
            super.onEnter();
            backgroup.setDuration(10 * 1000);
            backgroup.setValue(0f, 1f);
            backgroup.setInterpolator(new LinearInterpolator());
            backgroup.setRepeatCount(TimeValueEvent.INFINITE);
            getContext().addTimeEvent(backgroup);
        }

        @Override
        protected void onQuit() {
            super.onQuit();
            getContext().deleteTimeEvents();
        }

        @Override
        public void onDraw(Canvas canvas) {
            if (mBackgroupBitmap == null) {
                Bitmap bitmap = AssetsDirUtils.getAssetsBitmap(getContext().getContext(), "game_scene.png");
                float rate_height = getHeight() / (float) bitmap.getHeight();
                mBackgroupBitmap = Bitmap.createScaledBitmap(bitmap, (int) (rate_height * bitmap.getWidth()), (int) (rate_height * bitmap.getHeight()), true);
                bitmap.recycle();
            }

            final float value = backgroup.getValue();
            canvas.save();
            canvas.translate(0, getHeight() * value);
            canvas.drawBitmap(mBackgroupBitmap, 0, 0, mPaint);
            canvas.drawBitmap(mBackgroupBitmap, 0, -mBackgroupBitmap.getHeight(), mPaint);
            canvas.restore();


        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MASK:

                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            return false;
        }

        //背景动态
        TimeValueEvent backgroup = new TimeValueEvent();

    }


    /*爆炸效果*/
    private static final class ExplodeDecorations {
        Bitmap mExplode;
        Rect[] mSlice = new Rect[24];

        int count_list = 6;

        public ExplodeDecorations(Context c) {
            float[] boundary_x = new float[]{0f, 0.2f, 0.4f, 0.6f};

            mExplode = AssetsDirUtils.getAssetsBitmap(c, "game_click_explode.png");
            int witdh = mExplode.getWidth();
            int height = mExplode.getHeight();

            for (int line = 0; line < boundary_x.length; line++) {
                for (int list = 0; list < count_list; list++) {
                    int index = list + line * count_list;

                    Rect slice = new Rect();
                    slice.left = list * witdh / count_list;
                    slice.top = (int) (boundary_x[line] * height);
                    slice.right = slice.left + witdh / count_list;
                    if (line == boundary_x.length - 1) {
                        slice.bottom = height;
                    } else slice.bottom = slice.top + height / (boundary_x.length + 1);

                    mSlice[index] = slice;
                }
            }
        }
    }
}
