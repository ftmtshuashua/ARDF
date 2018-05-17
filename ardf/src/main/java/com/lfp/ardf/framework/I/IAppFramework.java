package com.lfp.ardf.framework.I;

/**
 * APP整体架构接口
 * Created by LiFuPing on 2017/11/16.
 */

public interface IAppFramework extends I1AppContextTransmit, ILifeCycleObserved, INetRequest {

    /**
     * 获得框架接口
     */
    IAppFramework getAppFk();
}
