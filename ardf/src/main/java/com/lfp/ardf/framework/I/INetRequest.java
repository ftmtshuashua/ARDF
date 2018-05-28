package com.lfp.ardf.framework.I;

import com.lfp.ardf.module.net.observer.IRequestObserver;
import com.lfp.ardf.module.net.request.IRequest;

/**
 * 网络请求URL
 * Created by LiFuPing on 2018/5/17.
 */
public interface INetRequest extends ILifeCycleObserved {

    <R extends IRequest, C extends IRequestObserver<R>> void request(C observer, R... request_chain);

    <R extends IRequest, C extends IRequestObserver<R>> void request(C observer, Iterable<R> request_chain);

}
