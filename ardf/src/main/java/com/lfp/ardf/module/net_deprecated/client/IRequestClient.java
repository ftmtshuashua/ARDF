package com.lfp.ardf.module.net_deprecated.client;

import com.lfp.ardf.module.net_deprecated.request.IRequest;

/**
 * 请求客户端<br/>
 * Created by LiFuPing on 2018/5/28.
 */
public interface IRequestClient<R extends IRequest> {

    /**
     * 执行具体的请求<br/>
     * 这个方法会在非UI线程中调用
     *
     * @param reqeust 具体请求
     * @return 请求结果
     */
    void perform(R reqeust) throws Exception ;

}
