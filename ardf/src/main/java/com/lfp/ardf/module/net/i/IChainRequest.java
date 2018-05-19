package com.lfp.ardf.module.net.i;

/**
 * 链式请求,将一堆请求合并为一个请求队列,依次执行
 * Created by LiFuPing on 2018/5/15.
 */
public interface IChainRequest {
    /**
     * 获得链中下一个请求
     */
    IChainRequest getNext();

    /**
     * 获得链中前一个请求
     */
    IChainRequest getPre();

    void setNext(IChainRequest mNext);

    void setPre(IChainRequest mPre);

    /**
     * 返回是否还有下一个请求
     *
     * @return true:有下一个请求，false:表示这是最后一个请求
     */
    boolean hasNext();

    /**
     * 返回当前请求前面是否有请求
     *
     * @return true:有，false:无
     */
    boolean hasPre();

    /**
     * 设置请求ID
     */
    void setId(int id);

    /**
     * @return 返回请求ID
     */
    int getId();

    /**
     * 替换请求链中下一个请求
     */
    void replaceNext(IChainRequest request);

    /**
     * 抛弃下一个请求
     */
    void removeNext();

    /**
     * 标记这个请求已经完成 - 并且已经拿到回复信息
     */
    void completed();

    /**
     * @return 返回这个请求的完成情况
     */
    boolean isCompleted();

    /**
     * @return 返回之后请求个数
     */
    int getRemainingCount();

    /**
     * 显示请求信息 . 在请求之前请求管理器会回调这个接口
     */
    void showReqeustLog();

    /**
     * 显示请求回复信息 , 当请求完成之后请求管理器回调此接口显示回复内容
     */
    void showResponseLog();


}
