package com.lfp.ardf.module.net.request;

/**
 * 链式请求,将一堆请求合并为一个请求队列,依次执行
 * Created by LiFuPing on 2018/5/15.
 */
public interface IChainRequest extends IRequest {
    /**
     * 获得链中下一个请求
     */
    IChainRequest getNext();

    /**
     * 获得链中前一个请求
     */
    IChainRequest getPre();

    void setNext(IChainRequest next);

    void setPre(IChainRequest pre);

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
     * 替换请求链中下一个请求
     */
    void replaceNext(IChainRequest request);

    /**
     * 抛弃下一个请求
     */
    void removeNext();

    /**
     * @return 返回剩余请求个数
     */
    int getRemainingCount();


}
