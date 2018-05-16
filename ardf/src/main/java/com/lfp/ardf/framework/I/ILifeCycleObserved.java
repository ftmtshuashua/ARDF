package com.lfp.ardf.framework.I;

/**
 * 核心功能 - 实现业务与Activity和Fragment的解耦
 * <p>
 * Created by LiFuPing on 2017/9/8.
 */

public interface ILifeCycleObserved {

    //注册监听器
    void registeredObserve(ILifeCycleObserve l);

    //移除监听器
    void unRegisteredObserve(ILifeCycleObserve l);
}
