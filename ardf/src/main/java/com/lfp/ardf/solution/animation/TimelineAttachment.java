package com.lfp.ardf.solution.animation;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * <pre>
 * desc:
 *      时间线上的附作物
 *
 * function:
 *
 * Created by LiFuPing on 2018/7/24.
 * </pre>
 */
@Deprecated
public class TimelineAttachment {

    /**
     * 无限期重复动画,只要它附着在时间线之上,将会一直收到事件流逝信息
     */
    public static final int REPEAT_COUNT_INFINITE = -1;

    /**
     * 重复计算,当一个时间周期结束之后,重新开始另一个时间周期事件
     */
    public static final int REPEAT_MODE_RESTART = 1;

    /**
     * 反向计算,当一个时间周期结束之后,反向开始另一个时间周期事件
     */
    public static final int REPEAT_MODE_REVERSE = 2;

    /**
     * 当附着物附着到时间线上的时候会携带此标记
     */
    private static final int LIFE_ATTACH = 0x1;

    /**
     * 附着物事件开始时携带此标记
     */
    private static final int LIFE_STARTED = 0x1 << 1;

    /**
     * 被计算值的起点
     */
    float mFromValue = 0.0f;

    /**
     * 被计算值的终点
     */
    float mToValue = 1.0f;

    /**
     * 附着物所附着的时间线
     */
    ITimeline mTimeline;

    /**
     * 时间偏移量
     */
    long mStartOffset;
    /**
     * 开始时间点,事件附加到时间线上的位置
     */
    long mStartTime = -1;
    /**
     * 流逝时间
     */
    long mTimeElapse = -1;
    /**
     * 一个动画周期的持续时间以毫秒为间隔
     */
    long mDuration;


    int flag;

    /**
     * 它控制计算的反转,在设置REPEAT_MODE_REVERSE模式的时候生效
     */
    boolean mCycleFlip = false;

    /**
     * 重复的模式 {@link #REPEAT_MODE_RESTART} or {@link #REPEAT_MODE_REVERSE}
     */
    int mRepeatMode = REPEAT_MODE_RESTART;
    /**
     * 动画必须重复的次数。默认情况下，永远重复下去。
     */
    int mRepeatCount = 0;

    /**
     * 表示动画重复了多少次
     */
    int mRepeated = 0;


    /**
     * The interpolator used by the animation to smooth the movement.
     */
    Interpolator mInterpolator;

    AttachmentListener mLinstener;

    /**
     * 实例化附着物
     */
    public TimelineAttachment() {

    }

    /**
     * 实例化带有默认范围的附着物
     *
     * @param fromValue 起点值
     * @param toValue   终点值
     */
    public TimelineAttachment(float fromValue, float toValue) {
        setValue(fromValue, toValue);
    }

    /**
     * 设置取值范围
     *
     * @param fromValue 起点值
     * @param toValue   终点值
     */
    public void setValue(float fromValue, float toValue) {
        mFromValue = fromValue;
        mToValue = toValue;
    }

    private void fireAtchmentStart() {
        if (mLinstener != null) {
            mLinstener.onAttachmentStart(this);
        }
    }

    private void fireAtchmentRepeat() {
        if (mLinstener != null) {
            mLinstener.onAttachmentRepeat(this);
        }
    }

    private void fireAtchmentEnd() {
        if (mLinstener != null) {
            mLinstener.onAttachmentEnd(this);
        }
    }

    /**
     * 获得时间线的流逝情况,根据自身属性计算合适的值
     *
     * @param currentTime 时间线时间
     */
    protected void elapse(long currentTime) {
        if (!isAttach()) return;
        if (mStartTime == -1) mStartTime = currentTime;
        mTimeElapse = currentTime;

        final long duration = getDuration();
        final long startOffset = getStartOffset();
        float normalizedTime;
        if (duration != 0) {
            normalizedTime = ((float) (currentTime - (mStartTime + startOffset))) / (float) duration;
        } else normalizedTime = currentTime < mStartTime ? 0.0f : 1.0f;


        final boolean expired = normalizedTime >= 1.0f || isDetach();
        // 0.0f ~ 1.0f
        normalizedTime = Math.max(Math.min(normalizedTime, 1.0f), 0.0f);

        if ((normalizedTime >= 0.0f) && (normalizedTime <= 1.0f)) {
            if (!isStared()) {
                flag |= LIFE_STARTED;
            }

            //反向
            if (mCycleFlip) normalizedTime = 1.0f - normalizedTime;

            if (mInterpolator == null) mInterpolator = new AccelerateDecelerateInterpolator();
            final float interpolatedTime = mInterpolator.getInterpolation(normalizedTime);

            final float fromValue = mFromValue;
            float value = fromValue + ((mToValue - fromValue) * interpolatedTime);

            if (mLinstener != null) mLinstener.onElapse(this, value);
        }


        if (expired) {
            if (mRepeatCount == mRepeated || isDetach()) {
                mTimeline.detach(this);
            } else {
                if (mRepeatCount > 0) mRepeated++;
                if (mRepeatMode == REPEAT_MODE_REVERSE) mCycleFlip = !mCycleFlip;
                mStartTime = -1;
                fireAtchmentRepeat();
            }
        }

    }

    /**
     * 清除附着物携带所有信息,还原为初始状态
     */
    public void clear() {
        mFromValue = 0.0f;
        mToValue = 1.0f;
        mTimeline = null;
        mStartOffset = 0;
        mStartTime = -1;
        mTimeElapse = -1;
        mDuration = 0;
        flag = 0;
        mCycleFlip = false;
        mInterpolator = null;
        mLinstener = null;

    }

    /**
     * 为了确保附着物能被重新附着到时间线上,做一些必要的重置操作
     */
    public void reset() {
        mTimeline = null;
        mStartTime = -1;
        mTimeElapse = -1;
        flag = 0;
        mCycleFlip = false;
    }


    /**
     * 获得附着到时间线会之后,所流逝的时间
     *
     * @return 流逝的时间
     */
    public long getElapse() {
        return mTimeElapse - mStartTime;
    }

    public long getStartOffset() {
        return mStartOffset;
    }

    public long getDuration() {
        return mDuration;
    }

    public boolean isAttach() {
        return (flag & LIFE_ATTACH) != 0;
    }

    public boolean isDetach() {
        return (flag & LIFE_ATTACH) == 0;
    }

    public boolean isStared() {
        return (flag & LIFE_STARTED) != 0;
    }

    /**
     * 附着到时间线
     *
     * @param timeline 时间线
     */
    protected void attachTimeline(ITimeline timeline) {
        this.mTimeline = timeline;
        flag |= LIFE_ATTACH;
        onAttach();
        fireAtchmentStart();
    }

    /**
     * 从时间线分离
     */
    protected void detachTimeline() {
        flag &= ~LIFE_ATTACH;
        onDetach();
        reset();
        fireAtchmentEnd();
    }

    /**
     * 当附着到时间线的时候
     */
    public void onAttach() {

    }

    /**
     * 当从时间线分离的时候
     */
    public void onDetach() {

    }

    /**
     * 设置一个时间周期的持续时间
     *
     * @param duration 一个时间周期的持续时间
     */
    public void setDuration(long duration) {
        mDuration = duration;
    }

    /**
     * 定义这个附着物在到达终点时应该做什么,它只有在RepeatCount大于0或者为
     * {@link #REPEAT_COUNT_INFINITE}的时候生效,默认为{@link #REPEAT_MODE_RESTART}
     *
     * @param repeatMode {@link #REPEAT_MODE_RESTART} or {@link #REPEAT_MODE_REVERSE}
     */
    public void setRepeatMode(int repeatMode) {
        this.mRepeatMode = repeatMode;
    }

    /**
     * 定义这个附着物计算的重复次数
     *
     * @param repeatCount {@link #REPEAT_COUNT_INFINITE} or Number >= 0
     */
    public void setRepeatCount(int repeatCount) {
        if (repeatCount < 0) repeatCount = REPEAT_COUNT_INFINITE;
        this.mRepeatCount = repeatCount;
    }

    /**
     * Sets the acceleration curve for this animation. Defaults to a linear
     * interpolation.
     *
     * @param i The interpolator which defines the acceleration curve
     * @attr ref android.R.styleable#Atchment_interpolator
     */
    public void setInterpolator(Interpolator i) {
        mInterpolator = i;
    }

    public void setAttachmentListener(AttachmentListener l) {
        mLinstener = l;
    }

    /**
     * 通知附着物事件的变化
     */
    public interface AttachmentListener {
        /**
         * 通知附着物事件的开始
         *
         * @param animation 开始的附着物
         */
        void onAttachmentStart(TimelineAttachment animation);

        /**
         * 通知附着物事件的结束
         *
         * @param animation 结束的附着物
         */
        void onAttachmentEnd(TimelineAttachment animation);

        /**
         * 通知附着物事件的重复
         *
         * @param animation 重复的附着物
         */
        void onAttachmentRepeat(TimelineAttachment animation);


        void onElapse(TimelineAttachment animation, float value);

    }
}
