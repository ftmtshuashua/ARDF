package com.lfp.ardf.module.net;

import com.lfp.ardf.module.net.i.IRequest;
import com.lfp.ardf.module.net.i.RequestListener;

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
public class RequestChain {

    private static final long FLAG_PREPARE = 0x1 << 32;
    private static final long FLAG_START = 0x1 << 33;
    private static final long FLAG_END = 0x1 << 34;
    private static final long FLAG_SHUTDOWN = 0x1 << 35;
    /*状态标记*/
    private static final long FLAG_STATE_MASK = 0xFF << 32;
    /*状态标记*/
    private static final long FLAG_REQUEST_ID_MASK = 0xFFFFFFFF;


    IRequest mRequestHead;
    RequestListener mRequestListener;

    long mFlag;

    private RequestChain(IRequest reqeust_head) {
        mRequestHead = reqeust_head;
    }

    public void setRequestListener(RequestListener l) {
        mRequestListener = l;
    }


    public static RequestChain request(IRequest... reqeust_array) {
        if (reqeust_array == null) return null;
        List<IRequest> array = new ArrayList<>();
        for (int i = 0; i < reqeust_array.length; i++) {
            IRequest reqeuset = reqeust_array[i];
            if (reqeuset == null) continue;
            reqeuset.setId(i);
            array.add(reqeuset);
        }
        return build(array);
    }

    public static RequestChain request(Iterable<IRequest> reqeust_array) {
        if (reqeust_array == null) return null;
        List<IRequest> array = new ArrayList<>();
        Iterator<IRequest> iterator = reqeust_array.iterator();
        int id = -1;
        while (iterator.hasNext()) {
            id++;
            IRequest request = iterator.next();
            if (request == null) continue;
            request.setId(id);
        }
        return build(array);
    }

    protected static RequestChain build(List<IRequest> reqeust_array) {
        if (reqeust_array == null || reqeust_array.isEmpty()) return null;
        for (int i = 0; i < reqeust_array.size() - 1; i++) {
            reqeust_array.get(i).setNextRequest(reqeust_array.get(i + 1));
        }
        return new RequestChain(reqeust_array.get(0));
    }

    public void start() {
        if (isRuning()) {
            throw new IllegalStateException("无法启动一个已经启动的请求链!");
        }
        mRequestHead.setRequestListener(requestMonitor);
        mRequestHead.start();
        setFlag(FLAG_PREPARE);
    }

    /*关闭请求链*/
    public void shutdown() {
        if (isRuning() && !isShutdown()) {
            IRequest request = mRequestHead;
            endReqeuestChain(request);
            while (request != null) {
                request.shutdown();
                request = request.getNextRequest();
            }
        }
    }

    public boolean isShutdown() {
        return (mFlag & FLAG_SHUTDOWN) != 0;
    }

    public boolean isRuning() {
        return (mFlag & FLAG_STATE_MASK) != 0;
    }

    public int getRequestId() {
        return (int) (mFlag & FLAG_REQUEST_ID_MASK);
    }


    RequestListener requestMonitor = new RequestListener() {
        @Override
        public void onStart(IRequest request) {
            if ((mFlag & FLAG_START) == 0) {
                setFlag(FLAG_START);
                if (mRequestListener != null) mRequestListener.onStart(request);
            }
            mFlag |= request.getId();
        }

        @Override
        public void onError(IRequest request, Throwable e) {
            if (mRequestListener != null) mRequestListener.onError(request, e);
            endReqeuestChain(request);
        }

        @Override
        public void onResponse(IRequest request) {
            if (mRequestListener != null) mRequestListener.onResponse(request);
        }

        @Override
        public void onComplete(IRequest request) {
            if (mRequestListener != null) mRequestListener.onComplete(request);
        }

        @Override
        public void onEnd(IRequest request) {
            if (isShutdown()) {
                endReqeuestChain(request);
            } else {
                if (request.hasNextRequest()) {
                    IRequest requet = request.getNextRequest();
                    requet.setRequestListener(requestMonitor);
                    requet.start();
                } else {
                    endReqeuestChain(request);
                }
            }
        }

    };

    void endReqeuestChain(IRequest request) {
        setFlag(FLAG_END | FLAG_SHUTDOWN);
        if (mRequestListener != null) mRequestListener.onEnd(request);
    }

    private void setFlag(long flag) {
        mFlag |= flag;
    }

}
