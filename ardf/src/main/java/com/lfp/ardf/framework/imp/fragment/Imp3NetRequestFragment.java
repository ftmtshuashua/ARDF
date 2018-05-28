package com.lfp.ardf.framework.imp.fragment;

import com.lfp.ardf.framework.I.INetRequest;
import com.lfp.ardf.module.net.observer.IRequestObserver;
import com.lfp.ardf.module.net.request.IRequest;

/**
 * Created by LiFuPing on 2018/5/17.
 */
public class Imp3NetRequestFragment extends Imp2LifeCycleObservedFragment implements INetRequest {
    @Override
    public <R extends IRequest, C extends IRequestObserver<R>> void request(C observer, R... request_chain) {

    }

    @Override
    public <R extends IRequest, C extends IRequestObserver<R>> void request(C observer, Iterable<R> request_chain) {

    }
}
