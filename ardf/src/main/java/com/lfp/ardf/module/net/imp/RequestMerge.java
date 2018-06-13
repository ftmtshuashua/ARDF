package com.lfp.ardf.module.net.imp;


import com.lfp.ardf.module.net.i.RequestListener;
import com.lfp.ardf.module.net.i.RequestNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 合并请求,并发调用<br/>
 * Created by LiFuPing on 2018/6/12.
 */
public final class RequestMerge extends RequestNode implements RequestListener {
    private static final int FLAG_PREPARE = 1;
    private static final int FLAG_STARTED = 3;
    private static final int FLAG_END = 7;
    private static final int FLAG_CALL_MASK = 0xF;

    private static final int FLAG_SHUTDOWN = 0x1 << 24;


    private List<RequestNode> merge_array;
    private int mflag;

    private RequestMerge() {

    }

    public static RequestMerge request(RequestNode... reqeust_array) {
        if (reqeust_array == null || reqeust_array.length == 0) return null;
        List<RequestNode> array = new ArrayList<>();
        for (int i = 0; i < reqeust_array.length; i++) {
            RequestNode request = reqeust_array[i];
            array.add(request);
            if (request != null) request.indexId(i);
        }
        RequestMerge mRequestMerge = new RequestMerge();
        mRequestMerge.merge_array = array;
        return mRequestMerge;
    }

    public static RequestMerge request(Iterable<RequestNode> reqeust_array) {
        if (reqeust_array == null) return null;
        Iterator<RequestNode> iterator = reqeust_array.iterator();
        List<RequestNode> array = new ArrayList<>();
        int id = 0;
        while (iterator.hasNext()) {
            RequestNode request = iterator.next();
            array.add(request);
            if (request != null) request.indexId(id);
            id++;
        }
        RequestMerge mRequestMerge = new RequestMerge();
        mRequestMerge.merge_array = array;
        return mRequestMerge;
    }


    @Override
    public void shutdown() {
        setFlag(FLAG_SHUTDOWN);
        if (isCalled() && isEnd()) {
            for (RequestNode request : merge_array) {
                if (request != null) {
                    request.shutdown();
                }
            }
            if ((mflag & FLAG_CALL_MASK) == FLAG_STARTED) notifyEnd(this);
        }
    }

    @Override
    public boolean isShutdown() {
        return (mflag & FLAG_SHUTDOWN) != 0;
    }

    @Override
    public void indexId(int id) {
        super.indexId(id);
        if (merge_array != null) {
            int offset = 0;
            for (RequestNode request : merge_array) {
                if (request == null) {
                    offset++;
                } else {
                    request.indexId(offset + id);
                    offset += request.length();
                }
            }
        }
    }

    @Override
    public int length() {
        int count = 0;
        if (merge_array == null) return count;
        for (RequestNode request : merge_array) {
            if (request == null) count++;
            else count += request.length();
        }
        return count;
    }


    void setFlag(int flag) {
        mflag |= flag;
    }

    /**
     * @return 请求是否已经启动过了
     */
    public boolean isCalled() {
        return (mflag & FLAG_CALL_MASK) != 0;
    }

    public boolean isEnd() {
        return (mflag & FLAG_CALL_MASK) == FLAG_END;
    }

    /*请求计数*/
    volatile int request_count = 0;

    @Override
    public final void start() {
        if (isShutdown()) return;

        if (!isCalled()) {
            setFlag(FLAG_PREPARE);
            List<RequestNode> request_array = new ArrayList<>();
            for (RequestNode request : merge_array) {
                if (request == null) continue;
                request.setRequestListener(this);
                request_array.add(request);

            }
            request_count = request_array.size();
            for (RequestNode request : request_array) {
                request.start();
            }
        }
    }

    @Override
    public void onStart(RequestNode request) {
        if ((mflag & FLAG_CALL_MASK) != FLAG_STARTED) {
            setFlag(FLAG_STARTED);
            notifyStart(this);
        }
    }

    @Override
    public void onError(RequestNode request, Throwable e) {
        notifyError(request, e);
        endRequest();
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
        endRequest();
    }

    private void endRequest() {
        if (isShutdown()) return;
        request_count--;
        if (request_count == 0) {
            setFlag(FLAG_END);
            notifyEnd(this);
        }
    }
}
