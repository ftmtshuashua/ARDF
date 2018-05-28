package com.lfp.ardf.framework.imp.activity;

import com.lfp.ardf.framework.I.INetRequest;
import com.lfp.ardf.module.net.observer.IRequestObserver;
import com.lfp.ardf.module.net.request.IRequest;

/**
 * Created by LiFuPing on 2018/5/17.
 */
public class Imp3NetRequestActivity extends Imp2LifeCycleObservedActivity implements INetRequest {
    @Override
    public <R extends IRequest, C extends IRequestObserver<R>> void request(C observer, R... request_chain) {

    }

    @Override
    public <R extends IRequest, C extends IRequestObserver<R>> void request(C observer, Iterable<R> request_chain) {

    }
}
