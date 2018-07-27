package com.lfp.ardf.widget.solution.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.lfp.ardf.exception.MsgException;
import com.lfp.ardf.util.ObjectReuseUtils;

import java.util.LinkedList;

/**
 * <pre>
 * desc:
 *      将View的生命周期看做一条时间线
 *      通过时间线上的附作物{@code TimelineAttachment},实现在某一段时间内处理一些信息
 *
 * function:
 *
 * Created by LiFuPing on 2018/7/24.
 * </pre>
 */
public class TimelineView extends View implements ITimeline {

    /*标示程序运行中*/
    static final int FLAG_RUNING = 0x1;

    /**
     * 附作物列表的,在列表中的附着物表示已经附着到时间线上
     */
    AttachmentList timeline = new AttachmentList();


    int mFlag;

    public TimelineView(Context context) {
        super(context);
    }

    public TimelineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TimelineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void attach(TimelineAttachment attachment) {
        if (attachment instanceof ReuseAttachment) {
            if (((ReuseAttachment) attachment).isRecycle())
                throw new MsgException("不能附加一个已经被回收的对象");
        }

        boolean attached = timeline.attach(this, attachment);
        if (attached && !isRuning()) ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    public void detach(TimelineAttachment attachment) {
        timeline.detach(attachment);
    }

    /**
     * 获得附着物列表
     *
     * @return 附着物列表
     */
    public <T extends TimelineAttachment> LinkedList<T> getAttachmentList() {
        return timeline.getAttachmentList();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getVisibility() != View.VISIBLE || timeline.isEmpty()) {
            mFlag &= ~FLAG_RUNING;
            return;
        }
        mFlag |= FLAG_RUNING;
        timeline.elapse(getDrawingTime());
        ViewCompat.postInvalidateOnAnimation(this);
    }

    /**
     * 查看View是否正在绘制,比如当有附着物被添加到时间线的时候,应该检查运行情况
     * ,如果未运行应该调用invalidate()
     *
     * @return 检查是否正在运行
     */
    public boolean isRuning() {
        return (mFlag & FLAG_RUNING) != 0;
    }


    ObjectReuseUtils<ReuseAttachment> mObjectReuseUtils = new ObjectReuseUtils<ReuseAttachment>() {
        @Override
        public ReuseAttachment obtain() {
            ReuseAttachment attachment = super.obtain();
            attachment.isRecycle = false;
            attachment.clear();
            return attachment;
        }

        @Override
        public ReuseAttachment create() {
            return new ReuseAttachment(this);
        }

        @Override
        public void recycle(ReuseAttachment obj) {
            super.recycle(obj);
            obj.isRecycle=true;
        }
    };

    /**
     * 获得附着物
     *
     * @return 时间线附着物
     */
    protected TimelineAttachment obtain() {
        return mObjectReuseUtils.obtain();
    }

    /**
     * 能被复用的附着物
     */
    protected static final class ReuseAttachment extends TimelineAttachment {
        ObjectReuseUtils mCache;
        boolean isRecycle = false;


        public ReuseAttachment(ObjectReuseUtils cache) {
            mCache = cache;
            setValue(0.0f, 1.0f);
        }

        @Override
        public void onDetach() {
            super.onDetach();
            mCache.recycle(this);
        }

        /**
         * 判断附着物是否已经被回收,已经被回收的附着物不能手动重新使用,
         * 它应该等待正确的调度方法来控制
         *
         * @return 判断附着物是否已经被回收
         */
        public boolean isRecycle() {
            return isRecycle;
        }
    }


}
