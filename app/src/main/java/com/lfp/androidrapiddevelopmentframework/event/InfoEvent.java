package com.lfp.androidrapiddevelopmentframework.event;

/**
 * 消息事件
 * Created by LiFuPing on 2018/5/11.
 */
public abstract class InfoEvent implements IEvent {
    String info;

    public InfoEvent(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
