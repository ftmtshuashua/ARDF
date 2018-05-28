package com.lfp.ardf.module.net.request;

/**
 * 链式请求实现<br/>
 * Created by LiFuPing on 2018/5/28.
 */
public abstract class ImpChainRequest<T extends ImpChainRequest> extends ImpRequest implements IChainRequest {
    /**
     * 标记忽略请求回复状态,包含这个标记的请求,顶层用户不会收到Response回复结果
     */
    public static final int FLAG_IGNORE_RESPONSE = 0x1;

    IChainRequest next;
    IChainRequest pre;

    int mFlag;

    @Override
    public IChainRequest getNext() {
        return next;
    }

    @Override
    public IChainRequest getPre() {
        return pre;
    }

    @Override
    public void setNext(IChainRequest next) {
        this.next = next;
    }

    @Override
    public void setPre(IChainRequest pre) {
        this.pre = pre;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public boolean hasPre() {
        return pre != null;
    }

    @Override
    public void replaceNext(IChainRequest request) {
        if (hasNext()) {
            request.setNext(next.getNext());
            request.setId(next.getId());
        }
        request.setPre(this);
        setNext(request);
    }

    @Override
    public void removeNext() {
        removeRequest(next);
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
     * @return 返回是否忽略这个请求的结果
     */
    public boolean isIgnoreResponse() {
        return (mFlag & FLAG_IGNORE_RESPONSE) != 0;
    }

    /**
     * 忽略当前请求的请求结果
     *
     * @param is 是否忽略当前请求的结果回复
     * @return OkHttpRequest
     */
    public T setIgnoreResponse(boolean is) {
        if (is) mFlag |= FLAG_IGNORE_RESPONSE;
        else mFlag &= ~FLAG_IGNORE_RESPONSE;
        return (T) this;
    }
}
