package com.lfp.ardf.framework.I;


/**
 * <pre>
 * desc:
 *       APP整体架构接口
 * function:
 *
 * Created by LiFuPing on 2017/11/16.
 * </pre>
 */
public interface IAppFramework extends I1AppContextTransmit, ILifeCycleObserved, INetRequest {

    /**
     * 获得App整体框架接口
     *
     * @return 框架接口
     */
    IAppFramework getAppFk();
}
