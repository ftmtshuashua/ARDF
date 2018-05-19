package com.lfp.ardf.module.net.i;

import java.io.IOException;

/**
 * 请求执行者,具体请求的实现人
 *
 * @param <R> 请求实现
 */
public interface IChainRequestHelper<R extends IChainRequest> {

    /**
     * 在非UI线程执行具体的请求
     *
     * @param request 具体请求
     * @return 请求结果
     */
    Object perform(R request) throws Exception;

    /**
     * 取消这个请求
     */
    void cancel(R request);
}
