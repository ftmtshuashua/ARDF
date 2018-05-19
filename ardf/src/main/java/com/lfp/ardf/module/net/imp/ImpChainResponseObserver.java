package com.lfp.ardf.module.net.imp;

import com.lfp.ardf.module.net.i.IChainRequest;
import com.lfp.ardf.module.net.i.IChainResponseObserver;

/**
 * Created by LiFuPing on 2018/5/19.
 */
public abstract class ImpChainResponseObserver<R extends IChainRequest, C extends ImpChainResponseObserver<R, C>> implements IChainResponseObserver<R> {
    /**
     * 标识这个请求已经关机,被关机的未完成请求已经之后的请求将被终止
     */
    public static final int FLAG_SHUTDOWN = 0x1 << 2;

    int flag;

    /*当前请求模式*/
    ReqeustModel requestModel = ReqeustModel.CHAIN;

    /**
     * 返回这个请求的运行状态
     */
    @Override
    public boolean isShutdown() {
        return (flag & FLAG_SHUTDOWN) != 0;
    }

    /**
     * 终止这个请求链.<br/>终止请求链之后,后面的请求会被舍弃,并且完成这个请求链！
     */
    public void shutdown() {
        flag |= FLAG_SHUTDOWN;
    }


    @Override
    public ReqeustModel getReqeustModel() {
        return requestModel;
    }

    /**
     * 设置请求模式
     *
     * @param model 请求模式
     * @return <C extends ImpChainResponseObserver<R, C>>
     */
    public C setReqeustModel(ReqeustModel model) {
        requestModel = model;
        return (C) this;
    }


}
