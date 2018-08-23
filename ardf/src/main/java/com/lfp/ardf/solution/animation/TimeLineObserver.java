package com.lfp.ardf.solution.animation;

/**
 * <pre>
 * desc:
 *      时间线观察者
 *
 *      当观察者被添加到一条时间线上的时候能观察到这条时间线上时间的流逝
 * function:
 *      isAttach()          :判断观察者对象是否正在观察一条时间线
 *      getTimeLine()       :获得被观察的时间线
 *      detach()            :取消对时间线的观察
 *      clear()             :清理数据
 *      onElapse()          :时间线上时间流逝的时候回调
 *      setOnChangeLisenter():设置观察者对象与时间线关系变化时候的监听
 * Created by LiFuPing on 2018/8/22.
 * </pre>
 */
public abstract class TimeLineObserver {
    /**
     * 附着的时间线.当观察者被添加到一条时间线上时,这个属性将被赋值为这条时间线
     */
    TimeLine mTimeLine;
    OnChangeLisenter mOnChangeLisenter;

    /**
     * 设置观察者对象与时间线关系变化时候的监听
     *
     * @param l 变化监听对象
     */
    public void setOnChangeLisenter(OnChangeLisenter l) {
        mOnChangeLisenter = l;
    }

    /**
     * 当观察者被添加到时间线上的时候回调此方法
     *
     * @param timeLine 被观察的时间线
     */
    protected void onAttach(TimeLine timeLine) {
        mTimeLine = timeLine;
        if (mOnChangeLisenter != null) mOnChangeLisenter.onAttach(mTimeLine, this);
    }

    /**
     * 当观察者从时间线上删除的时候回调此方法
     */
    protected void onDetach() {
        if (mOnChangeLisenter != null) mOnChangeLisenter.onAttach(mTimeLine, this);
        mTimeLine = null;
    }

    /**
     * 返回观察者是否被添加到了时间线中,用来判断观察者是否已经被添加了一条时间线.观察者对象同一时间不能同时观察多条时间线
     *
     * @return {@code true} 正在观察时间线
     */
    public boolean isAttach() {
        return mTimeLine != null;
    }

    /**
     * 获得观察者所观察的时间线
     *
     * @return 观察者所观察的时间线
     */
    public TimeLine getTimeLine() {
        return mTimeLine;
    }

    /**
     * 将这个观察者从正在观察的时间线上移除,移除之后将不能感知到时间的流逝
     */
    public void detach() {
        if (isAttach()) mTimeLine.deleteTimeObserver(this);
    }

    /**
     * 观察者对象可能会被复用,重写此方法并做一些必要的数据重置操作.
     */
    public void clear() {
    }

    /**
     * 如果观察者对象已经添加了一条时间线上,当时间线上的时间流逝的时候回回调此方法
     *
     * @param time 当前时间线的时间
     */
    public abstract void onElapse(long time);

    /**
     * 观察者对象与时间线关系改变的时候回调监听
     */
    public interface OnChangeLisenter {
        /**
         * 当观察者对象被添加到一条时间线上的时候回调
         *
         * @param timeline 观察者对象所附加的时间线
         * @param observer 变化的观察者对象
         */
        void onAttach(TimeLine timeline, TimeLineObserver observer);

        /**
         * 当观察者对象从时间线上移除的时候回调
         *
         * @param timeline 观察者对象所附加的时间线
         * @param observer 变化的观察者对象
         */
        void onDetach(TimeLine timeline, TimeLineObserver observer);
    }
}
