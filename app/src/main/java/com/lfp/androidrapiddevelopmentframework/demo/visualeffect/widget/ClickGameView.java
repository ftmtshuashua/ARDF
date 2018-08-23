package com.lfp.androidrapiddevelopmentframework.demo.visualeffect.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.util.AssetsDirUtils;
import com.lfp.ardf.util.BitmapUtils;
import com.lfp.ardf.util.ObjectReuseUtils;
import com.lfp.ardf.util.ScreenUtils;
import com.lfp.ardf.solution.animation.SimperAttachmentListener;
import com.lfp.ardf.solution.animation.TimelineAttachment;
import com.lfp.ardf.solution.animation.TimelineView2;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * desc:
 *      点击游戏实现
 * function:
 *
 * Created by LiFuPing on 2018/7/26.
 * </pre>
 */
public class ClickGameView extends TimelineView2 {

    public ClickGameView(Context context) {
        super(context);
        init();
    }

    public ClickGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClickGameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mExplodeDecorations = new ExplodeDecorations(getContext());

        mSceneArray.add(new SceneCombat(this));


        LogUtil.e(MessageFormat.format("View 大小:{0}x{1}", getWidth(), getHeight()));
    }

    List<Scene> mSceneArray = new ArrayList<>();

    Paint mPaint;
    ExplodeDecorations mExplodeDecorations;

    int scene_index = 0;

    //    Drawable drawable;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Scene scene = mSceneArray.get(scene_index);
        scene.onDraw(canvas, mPaint);

        List<Explode> data = getAttachmentList();
        for (int i = data.size() - 1; i >= 0; i--) {
            Explode mExplode = data.get(i);
            if (mExplode.isAttach()) {
                mExplode.onDraw(canvas, mPaint);
            }
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Explode mExplode = mExplodeObjectReuse.obtain();
            mExplode.init(mExplodeDecorations, event.getX(), event.getY());
            attach(mExplode);
        }
        return super.onTouchEvent(event);
    }


    /**
     * 游戏场景
     */
    private static abstract class Scene {

        public Scene(Context c) {
        }

        public abstract void onDraw(Canvas canvas, Paint paint);
    }

    private static final class SceneCombat extends Scene {

        Bitmap background;
        View mView;

        public SceneCombat(View v) {
            super(v.getContext());
            background = AssetsDirUtils.getAssetsBitmap(v.getContext(), "game_scene.jpg");
            mView = v;
            background = BitmapUtils.compressByScale(background, ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight(), true);
        }

        @Override
        public void onDraw(Canvas canvas, Paint paint) {
            int saveCount = canvas.save();
            int offset_x = (background.getWidth() - mView.getWidth()) / 2;
            canvas.translate(-offset_x, 0);
            canvas.drawBitmap(background, 0, 0, paint);
            canvas.restoreToCount(saveCount);
        }
    }


    ObjectReuseUtils<Explode> mExplodeObjectReuse = new ObjectReuseUtils<Explode>() {
        @Override
        public Explode create() {
            return new Explode(this);
        }
    };

    /*爆炸*/
    private static final class Explode extends TimelineAttachment {
        ExplodeDecorations mExplodeDecorations;
        float mTouchX, mTouchY;
        ObjectReuseUtils mObjectReuseUtils;

        public Explode(ObjectReuseUtils resuse) {
            mObjectReuseUtils = resuse;
            setDuration(600);
            setAttachmentListener(mExplodeAttachment);
        }

        public Explode(ExplodeDecorations explods, float touchx, float touchy) {
            init(explods, touchx, touchy);
        }

        public void init(ExplodeDecorations explods, float touchx, float touchy) {
            mExplodeDecorations = explods;
            mTouchX = touchx;
            mTouchY = touchy;
            setValue(0, mExplodeDecorations.mSlice.length - 1);
        }

        @Override
        public void onDetach() {
            super.onDetach();
            mObjectReuseUtils.recycle(this);
        }

        private void onDraw(Canvas canvas, Paint paint) {
            int index = (int) mExplodeAttachment.getValue();
            onDraw(mExplodeDecorations, canvas, paint, index);

        }

        private void onDraw(ExplodeDecorations explods, Canvas canvas, Paint paint, int index) {
            int saveCount = canvas.save();
            int offset = explods.mExplode.getWidth() / explods.count_list / 2;
            Rect rect = explods.mSlice[index];
            canvas.translate(-rect.left + mTouchX - offset, -rect.top + mTouchY - offset);
            canvas.clipRect(rect);
            canvas.drawBitmap(explods.mExplode, 0, 0, paint);

            canvas.restoreToCount(saveCount);
        }

        SimperAttachmentListener mExplodeAttachment = new SimperAttachmentListener();
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
