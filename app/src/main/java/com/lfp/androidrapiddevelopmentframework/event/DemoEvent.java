package com.lfp.androidrapiddevelopmentframework.event;

import com.lfp.ardf.framework.I.IAppFramework;

/**
 * Demo事件<br/>
 * Created by LiFuPing on 2018/6/6.
 */
public abstract class DemoEvent extends InfoEvent {
    IAppFramework mAppFk;
    String str_info;

    public DemoEvent(IAppFramework appfk, String title) {
        super(title);
        mAppFk = appfk;
    }

    public IAppFramework getAppFk() {
        return mAppFk;
    }

    public String getTitle() {
        return super.getInfo();
    }

    public void setInfo(String info) {
        str_info = info;
    }

    @Override
    public String getInfo() {
        return str_info;
    }
}
