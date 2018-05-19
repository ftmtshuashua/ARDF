package com.lfp.ardf.module.net.imp;

import com.lfp.ardf.module.net.i.IChainRequest;

/**
 * 链式请求实现
 * Created by LiFuPing on 2018/5/17.
 */
public abstract class ImpChainReqeust implements IChainRequest {

    /**
     * 标识这个请求已经完成，已完成的请求不应该再被操作
     */
    public static final int FLAG_COMPLETED = 0x1;
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

    @Override
    public IChainRequest getNext() {
        return next;
    }

    @Override
    public IChainRequest getPre() {
        return pre;
    }

    @Override
    public void setNext(IChainRequest mNext) {
        this.next = mNext;
    }

    @Override
    public void setPre(IChainRequest mPre) {
        this.pre = mPre;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public boolean hasPre() {
        return pre != null;
    }

    /**
     * 设置请求ID
     */
    @Override
    public void setId(int id) {
        if (isIdHave()) return;
        flag |= FLAG_ID_HAVE_ALLOCATED;
        this.id = id;
    }

    /**
     * 获得请求ID
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * 使用一个新请求替换请求链中下一个请求
     */
    @Override
    public void replaceNext(IChainRequest request) {
        next = request;
        request.setPre(this);
    }

    /**
     * 抛弃下一个请求
     */
    @Override
    public void removeNext() {
        removeRequest(next);
    }

    /**
     * 抛弃一个请求
     */
    public static void removeRequest(IChainRequest reqeust) {
        if (reqeust != null) {
            IChainRequest pre = reqeust.getPre();
            IChainRequest next = reqeust.getNext();
            if (pre != null) pre.setNext(next);
            if (next != null) next.setPre(pre);
        }
    }

    /**
     * 标记这个请求已经完成
     */
    @Override
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
    @Override
    public boolean isCompleted() {
        return (flag & FLAG_COMPLETED) != 0;
    }


    @Override
    public int getRemainingCount() {
        int count = 0;
        IChainRequest request = this;
        while (request.hasNext()) {
            request = request.getNext();
            count++;
        }
        return count;
    }

}
