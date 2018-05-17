package com.lfp.ardf.framework.imp.fragment;

import com.lfp.ardf.framework.I.INetRequest;
import com.lfp.ardf.module.net.ChainReqeustManager;
import com.lfp.ardf.module.net.i.IChainRequest;
import com.lfp.ardf.module.net.i.IChainResponseObserver;

/**
 * Created by LiFuPing on 2018/5/17.
 */
public class Imp3NetRequestFragment extends Imp2LifeCycleObservedFragment implements INetRequest {
    ChainReqeustManager mChainReqeustManager;

    @Override
    public <R extends IChainRequest, C extends IChainResponseObserver<R>> void request(C observer, R... request_chain) {
        if (mChainReqeustManager == null) mChainReqeustManager = ChainReqeustManager.defualt(this);
        mChainReqeustManager.request(observer, request_chain);
    }

    @Override
    public <R extends IChainRequest, C extends IChainResponseObserver<R>> void request(C observer, Iterable<R> request_chain) {
        if (mChainReqeustManager == null) mChainReqeustManager = ChainReqeustManager.defualt(this);
        mChainReqeustManager.request(observer, request_chain);
    }
}
