package com.lfp.ardf.module.net;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.module.net.i.IChainRequest;
import com.lfp.ardf.module.net.i.IChainResponseObserver;

/**
 * OkHttp 请求回复
 * Created by LiFuPing on 2018/5/17.
 */
public abstract class OkHttpResponse implements IChainResponseObserver<OkHttpRequest> {


    /**
     * 禁用并发请求<br/>
     * 启动这个选项将大大增加请求消耗的事件，建议不必要时不要开启
     */
    public static final int FLAG_DISABLE_CONCURRENT_REQUEST = 0x1 << 1;

    int mFlag;

    public void setFlag(int flg) {
        mFlag |= flg;
    }

    /**
     * @param disable 设置禁用并发请求
     */
    public OkHttpResponse setDisableConcurrentRequest(boolean disable) {
        if (disable) setFlag(FLAG_DISABLE_CONCURRENT_REQUEST);
        else mFlag &= ~FLAG_DISABLE_CONCURRENT_REQUEST;
        return this;
    }

    @Override
    public boolean isDisableConcurrentRequest() {
        return false;
    }


    @Override
    public OkHttpRequest getRequest() {
        return null;
    }

    @Override
    public void onStart() {
        LogUtil.e("OkHttpResponse -----> onStart");

    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e("OkHttpResponse -----> onError:"+e.getMessage());
    }

    @Override
    public void onChainComplete() {
        LogUtil.e("OkHttpResponse -----> onChainComplete");

    }

    @Override
    public void onDataProcessing(int id, IChainRequest request) {
        LogUtil.e("OkHttpResponse -----> onDataProcessing");
        //getRequest().isIgnoreResponse()
    }

    @Override
    public void onResponse(int id, IChainRequest request) {
        LogUtil.e("OkHttpResponse -----> onNext");

    }
}
