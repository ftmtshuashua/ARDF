package com.lfp.ardf.module.net;

import com.lfp.ardf.module.net.i.IChainResponseObserver;
import com.lfp.ardf.module.net.util.ReqeustExceptionUtil;
import com.lfp.ardf.util.SdkUtile;

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
    public void onChainStart() {
    }

    @Override
    public void onError(Throwable e) {

//                        3 exceptions occurred.
//                        e.printStackTrace();
        if (SdkUtile.has(SdkUtile.KITKAT)) {
//            Throwable[] array = e.getSuppressed();
            ReqeustExceptionUtil.getInstance().handle(e);
        } else {
            ReqeustExceptionUtil.getInstance().handle(e);
        }


    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onNotifyDataProcess(int id, OkHttpRequest request) {
        if (request.isIgnoreResponse()) return;
        onDataProcess(id, request);
    }

    /**
     * 耗时数据处理建议放到此方法下执行
     */
    public void onDataProcess(int id, OkHttpRequest request) {
    }

    @Override
    public void onNotifyResponse(int id, OkHttpRequest request) {
        if (request.isIgnoreResponse()) return;
        onResponse(id, request);
    }

    /**
     * 请求结果
     */
    public void onResponse(int id, OkHttpRequest request) {
    }


    @Override
    public void onChainEnd() {
    }


}
