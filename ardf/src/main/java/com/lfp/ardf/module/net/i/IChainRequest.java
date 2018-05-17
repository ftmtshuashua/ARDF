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
     * 终止这个请求链.<br/>终止请求链之后,后面的请求会被舍弃,并且完成这个请求链！
     */
    void shutdown();

    /**
     * 标记这个请求已经完成
     */
    void completed();

    /**
     * @return 返回这个请求的完成情况
     */
    boolean isCompleted();

    /**
     * @return 返回这个请求的运行状态
     */
    boolean isShutdown();

    /**
     * @return 返回之后请求个数
     */
    int getRemainingCount();
}
