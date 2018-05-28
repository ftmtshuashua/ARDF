package com.lfp.ardf.module.net.request;

/**
 * 批量请求<br/>
 * Created by LiFuPing on 2018/5/28.
 */
public interface IRequest {


    /**
     * 设置请求ID
     */
    void setId(int id);

    /**
     * @return 返回请求ID
     */
    int getId();

    /**
     * 取消这个请求
     */
    void cancel();

    /**
     * 显示请求日志，它将会在请求发起的时候打印到Logcat中
     */
    void showRequestLog();

    /**
     * 显示请求回复信息，它将会在请求结束的时候打印到Logcat中
     */
    void showResponseLog();

}
