package com.lfp.ardf.module.net.imp;

import com.lfp.ardf.module.net.i.RequestListener;
import com.lfp.ardf.module.net.i.RequestNode;
import com.lfp.ardf.module.net.util.IndexDistributor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 请求链条<br/>
 * 将多个请求节点进行串联,按顺序执行
 * 如果某个节点异常中断则链条断裂抛出错误信息并结束这条请求链
 * <p>
 * Created by LiFuPing on 2018/6/8.
 */
public final class RequestChain extends RequestNode implements RequestListener {

    private static final long FLAG_PREPARE = 1;
    private static final long FLAG_STARTED = 3;
    private static final long FLAG_END = 7;
    private static final long FLAG_CALL_MASK = 0xF;

    private static final long FLAG_SHUTDOWN = 0x10;

    List<RequestNode> chain_array;
    RequestNode mRequestHead;

    long mFlag;

    private RequestChain(List<RequestNode> reqeust_array, RequestNode reqeust_head) {
        chain_array = reqeust_array;
        mRequestHead = reqeust_head;
    }


    public static RequestChain request(RequestNode... reqeust_array) {
        List<RequestNode> array = new ArrayList<>();
        if (reqeust_array != null && reqeust_array.length > 0) {
            for (RequestNode reqeuset : reqeust_array) {
                array.add(reqeuset);
            }
        }
        return build(array);
    }

    public static RequestChain request(Iterable<RequestNode> reqeust_array) {
        List<RequestNode> array = new ArrayList<>();
        if (reqeust_array != null) {
            Iterator<RequestNode> iterator = reqeust_array.iterator();
            while (iterator.hasNext()) {
                array.add(iterator.next());
            }
        }
        return build(array);
    }

    protected static RequestChain build(List<RequestNode> reqeust_array) {
        RequestNode head = null;
        if (reqeust_array != null) {
            IndexDistributor.distribution(reqeust_array, 0);
            List<RequestNode> chain_array = new ArrayList<>();
            for (RequestNode request : reqeust_array) {
                if (request != null) chain_array.add(request);
            }
            for (int i = 0; i < chain_array.size() - 1; i++) {
                chain_array.get(i).setNext(chain_array.get(i + 1));
            }
            if (chain_array.size() > 1) head = chain_array.get(0);
        }
        return new RequestChain(reqeust_array, head);
    }


    @Override
    public void setIndex(int index) {
        super.setIndex(index);
        IndexDistributor.distribution(chain_array, index);
    }

    @Override
    public int length() {
        return IndexDistributor.nodeLenth(chain_array);
    }

    private void setFlag(long flag) {
        mFlag |= flag;
    }

    @Override
    public void start() {
        if (isShutdown()) return;
        if (!isCalled()) {
            setFlag(FLAG_PREPARE);
            mRequestHead.setRequestListener(this);
            mRequestHead.start();
        }
    }

    /*关闭请求链*/
    @Override
    public void shutdown() {
        setFlag(FLAG_SHUTDOWN);
        if (isCalled() && !isEnd()) {
            RequestNode request = mRequestHead;
            while (request != null) {
                request.shutdown();
                request = request.getNext();
            }
            if ((mFlag & FLAG_CALL_MASK) == FLAG_STARTED) notifyEnd(this);
        }
    }

    @Override
    public boolean isShutdown() {
        return (mFlag & FLAG_SHUTDOWN) != 0;
    }

    public boolean isCalled() {
        return (mFlag & FLAG_CALL_MASK) != 0;
    }

    public boolean isEnd() {
        return (mFlag & FLAG_CALL_MASK) != FLAG_END;
    }

    @Override
    public void onStart(RequestNode request) {
        if ((mFlag & FLAG_STARTED) != FLAG_STARTED) {
            setFlag(FLAG_STARTED);
            notifyStart(this);
        }
    }

    @Override
    public void onError(RequestNode request, Throwable e) {
        notifyError(request, e);
    }

    @Override
    public void onResponse(RequestNode request) {
        notifyResponse(request);
    }

    @Override
    public void onComplete(RequestNode request) {
        notifyComplete(request);
    }

    @Override
    public void onEnd(RequestNode request) {
        if (isShutdown() && request.isShutdown()) {
            endReqeuestChain();
        } else {
            if (request.hasNext()) {
                RequestNode requet = request.getNext();
                requet.setRequestListener(this);
                requet.start();
            } else {
                endReqeuestChain();
            }
        }
    }


    void endReqeuestChain() {
        setFlag(FLAG_END | FLAG_SHUTDOWN);
        notifyEnd(this);
    }

}
