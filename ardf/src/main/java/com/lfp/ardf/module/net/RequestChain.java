package com.lfp.ardf.module.net;

import com.lfp.ardf.module.net.i.IRequest;
import com.lfp.ardf.module.net.i.IRequestMonitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 请求链条<br/>
 * Created by LiFuPing on 2018/6/8.
 */
public class RequestChain {
    private static final int FLAG_RUNING = 0x1 << 16;
    /*状态标记*/
    private static final int FLAG_STATE_MASK = 0xf << 16;
    /*状态标记*/
    private static final int FLAG_ID_MASK = 0xffff;
    List<IRequest> mRequestArray;

    IRequestMonitor mRequestMonitor;

    int flag;

    private RequestChain(List<IRequest> reqeust_array) {
        mRequestArray = reqeust_array;
    }

    public void setRequestMonitor(IRequestMonitor l) {
        mRequestMonitor = l;
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
        return new RequestChain(reqeust_array);
    }

    public void start() {
        if (isRuning()) {
            throw new IllegalStateException("无法启动一个已经启动的请求链!");
        }
        IRequest requet = mRequestArray.get(0);
        requet.setMonitor(requestMonitor);
        requet.start();
    }

    /*关闭请求链*/
    public void shutdown() {
        if (mRequestArray == null) return;
        for (IRequest request : mRequestArray) {
            request.shutdown();
        }
    }

    public boolean isRuning() {
        return (flag & FLAG_RUNING) != 0;
    }

    public int getRequetId() {
        return flag & FLAG_ID_MASK;
    }

    IRequestMonitor requestMonitor = new IRequestMonitor() {
        @Override
        public void onStart(IRequest request) {
            if ((flag & FLAG_RUNING) == 0) {
                if (mRequestMonitor != null) mRequestMonitor.onStart(request);
                flag |= FLAG_RUNING;
            }
            flag |= request.getId();
        }

        @Override
        public void onError(IRequest request, Throwable e) {
            if (mRequestMonitor != null) mRequestMonitor.onError(request, e);
            endReqeuestChain(request);
        }

        @Override
        public void onComplete(IRequest request) {
            if (mRequestMonitor != null) mRequestMonitor.onComplete(request);
        }

        @Override
        public void onNext(IRequest request) {
            if (mRequestMonitor != null) mRequestMonitor.onNext(request);
        }

        @Override
        public void onEnd(IRequest request) {
            if (request.hasNextRequest()) {
                IRequest requet = request.getNextRequest();
                requet.setMonitor(requestMonitor);
                requet.start();
            } else {
                endReqeuestChain(request);
            }
        }

        void endReqeuestChain(IRequest request) {
            flag &= ~FLAG_STATE_MASK;
            if (mRequestMonitor != null) mRequestMonitor.onEnd(request);
        }
    };

}
