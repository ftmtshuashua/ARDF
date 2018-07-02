package com.lfp.ardf.framework.I;


/**
 * <pre>
 * desc:
 *     核心功能 - 实现业务与Activity和Fragment的解耦
 * function:
 *
 * Created by LiFuPing on 2017/9/8.
 * </pre>
 */
public interface ILifeCycleObserved {

    /**
     * 注册生命周期监听器
     *
     * @param l 监听器
     */
    void registeredObserve(ILifeCycleObserve l);

    /**
     * 移除生命周期监听器
     *
     * @param l 监听器
     */
    void unRegisteredObserve(ILifeCycleObserve l);
}
