package com.lfp.androidrapiddevelopmentframework.adapter.viewholder;

import com.lfp.androidrapiddevelopmentframework.event.InfoEvent;

/**
 * <pre>
 * desc:
 *
 * function:
 *
 * Created by LiFuPing on 2018/7/23.
 * </pre>
 */
public abstract class SwitchViewModel extends InfoEvent {
    boolean mSwitchModel = false;

    public SwitchViewModel(String info) {
        super(info);
    }


    /**
     * 设置当前状态
     *
     * @param model The switch state
     */
    public void setSwitchModel(boolean model) {
        mSwitchModel = model;
    }

    /**
     * 获得当前Switch状态
     *
     * @return The switch state
     */
    public boolean getSwitchModel() {
        return mSwitchModel;
    }

    /**
     * 当点击ItemView的时候回调函数
     */
    public abstract void onClick();

}
