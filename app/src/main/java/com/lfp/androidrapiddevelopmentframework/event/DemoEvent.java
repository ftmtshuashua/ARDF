package com.lfp.androidrapiddevelopmentframework.event;

import com.lfp.ardf.framework.I.IAppFramework;


/**
 * <pre>
 * desc:
 *      Demo事件
 * function:
 *      getInfo()       :获得附带信息
 *      setTitle()      :获得标题
 *
 * Created by LiFuPing on 2018/6/6.
 * </pre>
 */
public abstract class DemoEvent extends InfoEvent {
    IAppFramework mAppFk;
    String str_Title;

    public DemoEvent(IAppFramework appfk, String title, String info) {
        super(info);
        mAppFk = appfk;
        setTitle(title);
    }

    public IAppFramework getAppFk() {
        return mAppFk;
    }

    public void setTitle(String title) {
        str_Title = title;
    }

    public String getTitle() {
        return str_Title;
    }
}
