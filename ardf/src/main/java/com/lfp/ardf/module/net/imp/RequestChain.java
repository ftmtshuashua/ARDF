package com.lfp.ardf.module.net.imp;

import com.lfp.ardf.module.net.i.RequestListener;
import com.lfp.ardf.module.net.i.RequestNode;

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

    private static final long FLAG_PREPARE = 0x1 << 32;
    private static final long FLAG_START = 0x1 << 33;
    private static final long FLAG_END = 0x1 << 34;
    private static final long FLAG_SHUTDOWN = 0x1 << 35;
    /*状态标记*/
    private static final long FLAG_STATE_MASK = 0xFF << 32;
    /*状态标记*/
    private static final long FLAG_REQUEST_ID_MASK = 0xFFFFFFFF;

    RequestNode mRequestHead;

    long mFlag;

    private RequestChain(RequestNode reqeust_head) {
        mRequestHead = reqeust_head;
    }

    private void setFlag(long flag) {
        mFlag |= flag;
    }


    public static RequestChain request(RequestNode... reqeust_array) {
        if (reqeust_array == null) return null;
        List<RequestNode> array = new ArrayList<>();
        int id = 0;
        for (RequestNode reqeuset : reqeust_array) {
            if (reqeuset == null) {
                id++;
            } else {
                array.add(reqeuset);
                reqeuset.indexId(id);
                id += reqeuset.length();
            }
        }
        return build(array);
    }

    public static RequestChain request(Iterable<RequestNode> reqeust_array) {
        if (reqeust_array == null) return null;
        List<RequestNode> array = new ArrayList<>();
        Iterator<RequestNode> iterator = reqeust_array.iterator();
        int id = -1;
        while (iterator.hasNext()) {
            RequestNode request = iterator.next();
            if (request == null) {
                id++;
            } else {
                array.add(request);
                request.indexId(id);
                id += request.length();
            }
        }
        return build(array);
    }

    protected static RequestChain build(List<RequestNode> reqeust_array) {
        if (reqeust_array == null || reqeust_array.isEmpty()) return null;
        for (int i = 0; i < reqeust_array.size() - 1; i++) {
            reqeust_array.get(i).setNext(reqeust_array.get(i + 1));
        }
        return new RequestChain(reqeust_array.get(0));
    }

    @Override
    public void start() {
        if (isRuning()) {
            throw new IllegalStateException("无法启动一个已经启动的请求链!");
        }
        mRequestHead.setRequestListener(this);
        mRequestHead.start();
        setFlag(FLAG_PREPARE);
    }

    /*关闭请求链*/
    @Override
    public void shutdown() {
        if (isShutdown()) return;
        setFlag(FLAG_SHUTDOWN);
        if (isRuning()) {
            RequestNode request = mRequestHead;
            while (request != null) {
                request.shutdown();
                request = request.getNext();
            }
        }
    }

    @Override
    public boolean isShutdown() {
        return (mFlag & FLAG_SHUTDOWN) != 0;
    }

    public boolean isRuning() {
        return (mFlag & FLAG_STATE_MASK) != 0;
    }

    public int getRequestId() {
        return (int) (mFlag & FLAG_REQUEST_ID_MASK);
    }

    @Override
    public void onStart(RequestNode request) {
        if ((mFlag & FLAG_START) == 0) {
            setFlag(FLAG_START);
            notifyStart(this);
        }
        mFlag |= request.getId();
    }

    @Override
    public void onError(RequestNode request, Throwable e) {
        notifyError(request, e);
        endReqeuestChain(request);
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
            endReqeuestChain(request);
        } else {
            if (request.hasNext()) {
                RequestNode requet = request.getNext();
                requet.setRequestListener(this);
                requet.start();
            } else {
                endReqeuestChain(request);
            }
        }
    }


    void endReqeuestChain(RequestNode request) {
        setFlag(FLAG_END | FLAG_SHUTDOWN);
        notifyEnd(this);
    }

}
