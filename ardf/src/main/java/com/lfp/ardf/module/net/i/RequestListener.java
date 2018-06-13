package com.lfp.ardf.module.net.i;

/**
 * 请求监听器<br/>
 * 用于监控请求节点的状态
 * <p>
 * Created by LiFuPing on 2018/6/8.
 */
public interface RequestListener {

    /**
     * 当请求开始当时候回调,它只会被回调一次
     * 在这里做一些初始化操作
     *
     * @param request 首先执行的请求
     */
    void onStart(RequestNode request);

    /**
     * 当某个请求发生异常时回调
     * <p>
     * 在请求链中某个节点发生了错误,将会导致整个链条结束,并放弃执行之后当节点
     *
     * @param request 发生异常的请求
     * @param e       异常信息
     */
    void onError(RequestNode request, Throwable e);

    /**
     * 当请求成功之后会在请求发生线程中回调此方法
     * <p>
     * 请求一般发生在子线程,所以可以在这里进行一些耗时当操作.它不会阻塞UI线程
     *
     * @param request 完成当请求
     */
    void onResponse(RequestNode request);

    /**
     * 当请求完成之后在UI线程回调此方法.
     * <p>
     * 经过onResponse()方法对耗时操作的处理之后,将数据展示在UI上.
     * 这样做当好处是使得用户不会感觉到任何卡顿
     *
     * @param request
     */
    void onComplete(RequestNode request);

    /**
     * 当请求结束当时候回调,它只会被回调一次
     *
     * @param request 最后执行的请求
     */
    void onEnd(RequestNode request);
}
