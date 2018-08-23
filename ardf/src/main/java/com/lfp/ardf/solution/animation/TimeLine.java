package com.lfp.ardf.solution.animation;

import java.util.Vector;

/**
 * <pre>
 * desc:
 *      这个对象用来表示一条时间线
 *      通过观察者对象可以观察到时间的流逝,
 *
 * function:
 *      addTimeObserver()       :添加时间观察者
 *      deleteTimeObserver()    ;移除时间观察者
 *      deleteTimeObservers()   :移除所有观察者
 *      elapse()                :时间流逝
 *
 * Created by LiFuPing on 2018/8/22.
 * </pre>
 */
public class TimeLine<T extends TimeLineObserver> {
    private Vector<T> observer_array;

    public TimeLine() {
        this.observer_array = new Vector<>();
    }

    /**
     * 添加时间观察者,通过观察时间的流逝可以做一些事情.
     * 观察者对象不能同时观察多条时间线
     *
     * @param observer 时间观察者
     */
    public synchronized void addTimeObserver(T observer) {
        if (observer == null) throw new NullPointerException();
        if (observer_array.contains(observer)) return;
        if (observer.isAttach()) throw new IllegalArgumentException("观察者对象不能同时观察多条时间线!");
        observer_array.addElement(observer);
        observer.onAttach(this);
    }

    /**
     * 移除已经添加到时间线的观察者对象
     *
     * @param observer 时间观察者对象
     */
    public synchronized void deleteTimeObserver(T observer) {
        if (!observer_array.contains(observer)) return;
        observer_array.removeElement(observer);
        observer.onDetach();
    }

    /**
     * 清除观察者列表，使这条时间线不再有任何观察者
     */
    public synchronized void deleteTimeObservers() {
        Object[] arrLocal = observer_array.toArray();
        observer_array.removeAllElements();
        for (int i = arrLocal.length - 1; i >= 0; i--)
            ((T) arrLocal[i]).onDetach();
    }

    /**
     * 获得时间线上观察者的数目
     *
     * @return 添加到这个时间线中的观察者的数目
     */
    public int getTimeObserverCount() {
        return observer_array.size();
    }

    /**
     * 感知真实时间的流逝,当真实时间发生变化的时候应该调用此方法.
     *
     * 时间信号发送频率应该控制在一个合适的值
     *
     *
     * @param time 流逝的时间
     */
    public void elapse(long time) {
        if (observer_array.isEmpty()) return;
        Object[] arrLocal;
        synchronized (this) {
            arrLocal = observer_array.toArray();
        }

        for (int i = arrLocal.length - 1; i >= 0; i--)
            ((T) arrLocal[i]).onElapse(time);
    }

}
