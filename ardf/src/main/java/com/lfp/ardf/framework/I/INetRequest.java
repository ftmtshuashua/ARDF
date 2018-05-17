package com.lfp.ardf.framework.I;

import com.lfp.ardf.module.net.i.IChainRequest;
import com.lfp.ardf.module.net.i.IChainResponseObserver;

/**
 * 网络请求URL
 * Created by LiFuPing on 2018/5/17.
 */
public interface INetRequest extends ILifeCycleObserved {

    <R extends IChainRequest, C extends IChainResponseObserver<R>> void request(C observer, R... request_chain);

    <R extends IChainRequest, C extends IChainResponseObserver<R>> void request(C observer, Iterable<R> request_chain);

}
