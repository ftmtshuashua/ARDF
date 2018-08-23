package com.lfp.ardf.solution.animation;

/**
 * <pre>
 * desc:
 *          时间与数据关系事件,通过getValue()方法获取当前时间点对象的数据
 *
 * function:
 *      getValue()      :获得当前时间点对应的数据
 *      setValue()      :设置取值范围
 *      getFromValue()  :获得取值范围起点
 *      getToValue()    :获得取值范围终点
 *
 * Created by LiFuPing on 2018/8/23.
 * </pre>
 */
public class TimeValueEvent extends TimeEvent {


    /**
     * 被计算值的起点
     */
    float mFromValue;

    /**
     * 被计算值的终点
     */
    float mToValue;

    float mValue;

    /**
     * 构建一个默认取值 (0.0f ~ 1.0f) 的事件
     */
    public TimeValueEvent() {
        this(0.0f, 1.0f);
    }

    /**
     * 构建自定义取值事件
     *
     * @param fromValue 起始值
     * @param toValue   结束值
     */
    public TimeValueEvent(float fromValue, float toValue) {
        setValue(fromValue, toValue);
    }

    /**
     * 设置取值范围
     *
     * @param fromValue 起始值
     * @param toValue   结束值
     */
    public void setValue(float fromValue, float toValue) {
        this.mFromValue = fromValue;
        this.mToValue = toValue;
    }

    /**
     * 当前时间点对应的值
     *
     * @return 时间点对应的值
     */
    public float getValue() {
        return mValue;
    }

    @Override
    public void onElapse(long actualtime, long runtime, long duration, float progress) {
        final float fromValue = mFromValue;
        mValue = fromValue + ((mToValue - fromValue) * getInterpolator().getInterpolation(progress));
    }


    /**
     * 数据取值范围起点
     *
     * @return 范围起点
     */
    public float getFromValue() {
        return mFromValue;
    }

    /**
     * 数据取值范围终点
     *
     * @return 范围终点
     */
    public float getToValue() {
        return mToValue;
    }

}
