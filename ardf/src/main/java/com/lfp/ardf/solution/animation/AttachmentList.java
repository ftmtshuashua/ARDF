package com.lfp.ardf.solution.animation;

import com.lfp.ardf.debug.LogUtil;

import java.text.MessageFormat;
import java.util.LinkedList;

/**
 * <pre>
 * desc:
 *      附作物列表,在列表中的附着物表示已经附着到时间线上
 * function:
 *
 * Created by LiFuPing on 2018/7/25.
 * </pre>
 */
@Deprecated
public class AttachmentList<T extends TimelineAttachment> {
    /*真实的开始时间,对应物理世界时间*/
    long mRealStartTime = -1;
    /*开始时间偏移量*/
    long mStartOffset = -1;
    /*时间线的开始点*/
    long mStartTime = -1;
    /*流逝时间*/
    long mTimeElapse = -1;

    /*表示时间线上的附作物*/
    private transient LinkedList<T> mAttachment = new LinkedList<>();

    /**
     * 当时间线上没有附着物的时候返回True
     *
     * @return {@code true}时间线上没有附着物
     */
    public boolean isEmpty() {
        return mAttachment.isEmpty();
    }

    /**
     * 将附作物添加到附作物列表
     *
     * @param attachment
     * @return {@code true} 附着成功
     */
    public boolean attach(ITimeline timeline, T attachment) {
        boolean suc = mAttachment.add(attachment);
        LogUtil.e(MessageFormat.format("添加附着物:{0}   size:{1}", suc, mAttachment.size()));
        if (suc) attachment.attachTimeline(timeline);
        return suc;
    }

    /**
     * 分离附着在时间线上的附着物
     *
     * @param attachment 附着物
     * @return {@code true} 分离成功
     */
    public boolean detach(TimelineAttachment attachment) {
        boolean suc = mAttachment.remove(attachment);
        LogUtil.e(MessageFormat.format("分离附着物:{0}   size:{1}", suc, mAttachment.size()));
        if (suc) attachment.detachTimeline();
        return suc;
    }

    /**
     * 时间线时间流逝
     *
     * @param time 流逝的时间
     */
    public void elapse(long time) {
        if (mStartTime == -1) {
            mStartTime = time;
            mRealStartTime = System.currentTimeMillis();
        }
        mTimeElapse = time;
        if (!isEmpty()) {
            for (int index = mAttachment.size() - 1; index >= 0; index--) {
                TimelineAttachment attachment = mAttachment.get(index);
                attachment.elapse(time);
            }
        }
    }

    /**
     * 获得附着物列表
     *
     * @return 附着物列表
     */
    public LinkedList<T> getAttachmentList() {
        return mAttachment;
    }

    /**
     * 获得时间线开始到当前时间所流逝的时间,单位毫秒
     *
     * @return 流逝的时间
     */
    public long getElapse() {
        return mTimeElapse - mStartTime;
    }

    /**
     * 获得时间线的开始时间
     *
     * @return 时间线的开始时间
     */
    public long getStartTime() {
        return mStartTime;
    }

    /**
     * 获得时间线开始的真实时间
     *
     * @return 真实的时间
     */
    public long getRealStartTime() {
        return mRealStartTime;
    }

    /**
     * 获得开始时间偏移量
     *
     * @return 开始时间偏移量
     */
    public long getStartOffset() {
        return mStartOffset;
    }
}
