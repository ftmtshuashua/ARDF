package com.lfp.ardf.module.net;

import com.lfp.ardf.module.net.imp.ImpChainResponseObserver;
import com.lfp.ardf.module.net.util.ExceptionTotalUtil;
import com.lfp.ardf.util.SdkUtile;

/**
 * OkHttp 请求回复
 * Created by LiFuPing on 2018/5/17.
 */
public abstract class OkHttpResponse extends ImpChainResponseObserver<OkHttpRequest, OkHttpResponse> {

    int mFlag;
    public void setFlag(int flg) {
        mFlag |= flg;
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
            ExceptionTotalUtil.handle(e);
        } else {
            ExceptionTotalUtil.handle(e);
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
