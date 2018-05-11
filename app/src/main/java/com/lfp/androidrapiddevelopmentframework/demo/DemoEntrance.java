package com.lfp.androidrapiddevelopmentframework.demo;

/**
 * Demo 入口
 * Created by LiFuPing on 2018/5/11.
 */
public abstract class DemoEntrance {
    String info;

    public DemoEntrance(String info) {
        this.info = info;
    }

    public abstract void enter();

    public String getInfo() {
        return info;
    }
}
