package com.lfp.androidrapiddevelopmentframework.event;

/**
 * <pre>
 * desc:
 *      带消息事件
 * function:
 *      getInfo()      :获得消息
 *
 * Created by LiFuPing on 2018/5/11..
 * </pre>
 */
public abstract class InfoEvent implements IEvent {
    String info;

    public InfoEvent(String info) {
        this.info = info;
    }

    /**
     * 设置消息
     * @param info The info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * 获得消息
     *
     * @return The info
     */
    public String getInfo() {
        return info;
    }
}
