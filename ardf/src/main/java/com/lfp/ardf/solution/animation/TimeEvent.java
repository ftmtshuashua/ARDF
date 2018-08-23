package com.lfp.ardf.solution.animation;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.support.annotation.InterpolatorRes;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/**
 * <pre>
 * desc:
 *      观察时间线上一段时间的流逝,转换成周期性变化的时间
 *
 * function:
 *      onRestart()         :事件重新开始时回调
 *      getDuration()       :获得一次事件的持续时间
 *      setDuration()       :设置一次事件的持续时间
 *      getStartTime()      :获得事件的开始时间
 *      setRepeatMode()     :设置事件重复模式
 *      getRepeatMode()     :获得事件重复模式
 *      setRepeatCount()    :设置事件重复次数
 *      getRepeatCount()    :获得事件重复次数
 *      setInterpolator()   :设置插值器
 *      getInterpolator()   ;获得插值器
 *
 *
 * Created by LiFuPing on 2018/8/22.
 * </pre>
 */
public abstract class TimeEvent extends TimeLineObserver {

    /**
     * 无限的 - 表示事件将无限重复允许.直到显示的调用detach()方法将事件从时间线上移除
     */
    public static final int INFINITE = -1;

    /**
     * 重新开始 - 当事件重复次数大于 0 时.重新开始这个事件
     */
    public static final int RESTART = 1;

    /**
     * 反向开始 - 当事件重复次数大于 0 时.重新开始这个事件,并且方向是和前一次相反
     */
    public static final int REVERSE = 2;


    /**
     * 记录事件开始开始的时间点
     */
    long mStartTime = -1;

    /**
     * 记录时间偏移量 , 当事件重新开始的时候,使用偏移量来矫正时间
     */
    long mStartTimeOffset;

    /**
     * 一次事件的持续时间
     */
    long mDuration;

    /**
     * 它控制计算的反转,在设置REPEAT_MODE_REVERSE模式的时候生效
     */
    boolean mCycleFlip = false;

    /**
     * 事件重复模式 {@link #RESTART} or {@link #REVERSE}
     */
    int mRepeatMode = RESTART;
    /**
     * 动画必须重复的次数。默认情况下，永远重复下去。
     */
    int mRepeatCount = 0;
    /**
     * 记录动画重复了多少次
     */
    int mCurrentRepeatedCount = 0;

    /**
     * 使用的插值器来平滑过度
     */
    private Interpolator mInterpolator;

    @Override
    public final void onElapse(long time) {
        if (mStartTime == -1) mStartTime = time;
        final long duration = getDuration();
        final long startOffset = mStartTimeOffset;

        long runtime;
        float periodrate;

        if (duration > 0) {
            runtime = time - mStartTime - startOffset; //事件在一个周期内的运行时间
            periodrate = runtime / (float) duration; //事件在一个周期内 已经运行时间的比例 0.0f ~ 1.0f
        } else {
            periodrate = 1.0f;
        }

        final boolean expired = periodrate >= 1.0f; //一次事件过期
        periodrate = Math.max(Math.min(periodrate, 1.0f), 0.0f); //保证取值范围在 0.0f ~ 1.0f
        runtime = (long) (duration * periodrate);//保证运行时间与运行比例的一致性

        if (mCycleFlip) periodrate = 1.0f - periodrate;

        onElapse(time, runtime, duration, periodrate);


        if (expired) { /*当一次事件过期的时候*/
            if (mRepeatCount == mCurrentRepeatedCount) { /*达到了事件的最大重复次数*/
                detach();
            } else {
                if (mRepeatCount > 0) mCurrentRepeatedCount++;
                if (mRepeatMode == REVERSE) mCycleFlip = !mCycleFlip;
                mStartTime = -1;
                onRestart();
            }
        }
    }

    /**
     * 时间流逝回调
     *
     * @param actualtime 时间线上的真实时间
     * @param runtime    一次事件已经运行的时间
     * @param duration   一次事件的周期时间
     * @param progress   事件进度(0.0f ~ 1.0f)
     */
    public abstract void onElapse(long actualtime, long runtime, long duration, float progress);

    /**
     * 当事件从新开始的时候回调方法
     */
    public void onRestart() {

    }

    /**
     * 获得一次事件的持续时间
     *
     * @return 持续时间
     */
    public long getDuration() {
        return mDuration;
    }

    /**
     * 设置一次事件的持续时间
     *
     * @param durationMillis 持续时间
     */
    public void setDuration(long durationMillis) {
        if (durationMillis < 0) {
            throw new IllegalArgumentException("TimeEvent 持续时间不能为负数");
        }
        this.mDuration = durationMillis;
    }

    /**
     * 获得事件开始执行的时候
     *
     * @return 这个事件开始执行的时候
     */
    public long getStartTime() {
        return mStartTime;
    }

    /**
     * 设置事件的重复模式
     *
     * @param mRepeatMode {@link #RESTART} or {@link #REVERSE}
     */
    public void setRepeatMode(int mRepeatMode) {
        this.mRepeatMode = mRepeatMode;
    }

    /**
     * 获得事件的重复模式
     *
     * @return 这个事件的重复模式
     */
    public int getRepeatMode() {
        return mRepeatMode;
    }

    /**
     * 设置事件的重复次数,无限循环使用{@link #INFINITE}
     *
     * @param repeatCount 重复次数
     */
    public void setRepeatCount(int repeatCount) {
        this.mRepeatCount = repeatCount;
    }

    /**
     * 获得事件的重复次数
     *
     * @return 这个事件的重复次数
     */
    public int getRepeatCount() {
        return mRepeatCount;
    }


    /**
     * Sets the acceleration curve for this animation. The interpolator is loaded as
     * a resource from the specified context.
     *
     * @param context The application environment
     * @param resID   The resource identifier of the interpolator to load
     * @attr ref android.R.styleable#Animation_interpolator
     */
    public void setInterpolator(Context context, @AnimRes @InterpolatorRes int resID) {
        setInterpolator(AnimationUtils.loadInterpolator(context, resID));
    }

    /**
     * Sets the acceleration curve for this animation. Defaults to a linear
     * interpolation.
     *
     * @param i The interpolator which defines the acceleration curve
     * @attr ref android.R.styleable#Animation_interpolator
     */
    public void setInterpolator(Interpolator i) {
        this.mInterpolator = i;
    }

    /**
     * Gets the acceleration curve type for this animation.
     *
     * @return the {@link Interpolator} associated to this animation
     * @attr ref android.R.styleable#Animation_interpolator
     */
    public Interpolator getInterpolator() {
        if (mInterpolator == null) mInterpolator = DefualtInterpolator.getInstance();
        return mInterpolator;
    }

    @Override
    public void clear() {
        super.clear();
        mStartTime = -1;
        mStartTimeOffset = 0;
        mDuration = 0;
        mCycleFlip = false;
        mRepeatMode = RESTART;
        mRepeatCount = 0;
        mCurrentRepeatedCount = 0;
        mInterpolator = null;
    }

    /*默认插值器*/
    private static final class DefualtInterpolator extends AccelerateDecelerateInterpolator {
        static DefualtInterpolator mDefualtInterpolator;

        public static DefualtInterpolator getInstance() {

            if (mDefualtInterpolator == null) {
                synchronized (DefualtInterpolator.class) {
                    if (mDefualtInterpolator == null)
                        mDefualtInterpolator = new DefualtInterpolator();
                }
            }

            return mDefualtInterpolator;
        }

    }
}
