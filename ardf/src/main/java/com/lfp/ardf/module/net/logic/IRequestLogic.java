package com.lfp.ardf.module.net.logic;

import com.lfp.ardf.module.net.client.IRequestClient;
import com.lfp.ardf.module.net.observer.IRequestObserver;
import com.lfp.ardf.module.net.request.IRequest;

/**
 * 请求逻辑 - 批量请求的处理方式实现<br/>
 * Created by LiFuPing on 2018/5/28.
 */
public interface IRequestLogic<R extends IRequest> {
    /**
     * @param client 设置请求客户端
     */
    void setRequestClient(IRequestClient<R> client);

    /**
     * 执行请求
     *
     * @param observer      请求观察者
     * @param reqeust_array 请求列表
     */
    void perform(IRequestObserver<R> observer, R... reqeust_array);

    /**
     * 执行请求
     *
     * @param observer      请求观察者
     * @param reqeust_array 请求列表
     */
    void perform(IRequestObserver<R> observer, Iterable<R> reqeust_array);


    /**
     * 关闭请求
     */
    void shutdown();


}
