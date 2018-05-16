package com.lfp.ardf.module.net.i;

/**
 * 链式请求,将一堆请求合并为一个请求队列,依次执行
 * Created by LiFuPing on 2018/5/15.
 */
public abstract class IChainRequest<T extends IChainRequest> {
    /**
     * 标识这个请求已经完成，已完成的请求不应该再被操作
     */
    public static final int FLAG_COMPLETED = 0x1;
    /**
     * 标识这个请求已经关机,被关机的未完成请求已经之后的请求将被终止
     */
    public static final int FLAG_SHUTDOWN = 0x1 << 1;
    /**
     * 标记ID已经分配,如果为分配ID系统会按顺序分配一个以0开始递增的ID
     */
    public static final int FLAG_ID_HAVE_ALLOCATED = 0x1 << 2;

    IChainRequest next;
    IChainRequest pre;

    /**
     * 请求ID，用于区别请求,获取请求结果的时候可以通过ID来判断是哪个请求
     */
    int id;

    int flag;

    public IChainRequest getNext() {
        return next;
    }

    public IChainRequest getPre() {
        return pre;
    }

    public void setNext(IChainRequest mNext) {
        this.next = mNext;
    }

    public void setPre(IChainRequest mPre) {
        this.pre = mPre;
    }

    public boolean hasNext() {
        return next != null;
    }

    public boolean hasPre() {
        return pre != null;
    }

    /**
     * 设置请求ID
     */
    public IChainRequest setId(int id) {
        if (isIdHave()) return this;
        flag |= FLAG_ID_HAVE_ALLOCATED;
        this.id = id;
        return this;
    }

    /**
     * 获得请求ID
     */
    public int getId() {
        return id;
    }

    /**
     * 替换请求链中下一个请求
     */
    public void replaceNext(IChainRequest request) {
        if (isShutdown()) throw new IllegalStateException("替换失败，当前请求链已关机!");
        next = request;
        request.pre = this;
    }

    /**
     * 抛弃下一个请求
     */
    public void removeNext() {
        removeRequest(next);
    }

    /**
     * 抛弃一个请求
     */
    public static void removeRequest(IChainRequest reqeust) {
        if (reqeust != null) {
            IChainRequest pre = reqeust.pre;
            IChainRequest next = reqeust.next;
            if (pre != null) pre.next = reqeust.next;
            if (next != null) next.pre = pre;
        }
    }

    /**
     * 终止这个请求链.<br/>终止请求链之后,后面的请求会被舍弃,并且完成这个请求链！
     */
    public void shutdown() {
        flag |= FLAG_SHUTDOWN;
        if (next != null) next.shutdown();
        next = null;
    }

    /**
     * 标记这个请求已经完成
     */
    public void completed() {
        flag |= FLAG_COMPLETED;
    }

    /**
     * 返回ID状态，保证这个ID只会被分配一次
     */
    public boolean isIdHave() {
        return (flag & FLAG_ID_HAVE_ALLOCATED) != 0;
    }

    /**
     * 返回这个请求的完成情况
     */
    public boolean isCompleted() {
        return (flag & FLAG_COMPLETED) != 0;
    }

    /**
     * 返回这个请求的运行状态
     */
    public boolean isShutdown() {
        return (flag & FLAG_SHUTDOWN) != 0;
    }

}
