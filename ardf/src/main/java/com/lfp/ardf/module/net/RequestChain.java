package com.lfp.ardf.module.net;

import com.lfp.ardf.module.net.i.IRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 请求链条<br/>
 * Created by LiFuPing on 2018/6/8.
 */
public class RequestChain {
    List<IRequest> mRequestArray;

    public void request(IRequest... reqeust_array) {
        if (reqeust_array == null) return;
        List<IRequest> array = new ArrayList<>();
        for (int i = 0; i < reqeust_array.length; i++) {
            IRequest reqeuset = reqeust_array[i];
            if (reqeuset == null) continue;
            reqeuset.setId(i);
            array.add(reqeuset);
        }
        start(array);
    }

    public void request(Iterable<IRequest> reqeust_array) {
        if (reqeust_array == null) return;
        List<IRequest> array = new ArrayList<>();
        Iterator<IRequest> iterator = reqeust_array.iterator();
        int id = -1;
        while (iterator.hasNext()) {
            id++;
            IRequest request = iterator.next();
            if (request == null) continue;
            request.setId(id);
        }
        start(array);
    }

    protected void start(List<IRequest> reqeust_array) {
        if (reqeust_array == null || reqeust_array.isEmpty()) return;
        for (int i = 0; i < reqeust_array.size() - 1; i++) {
            reqeust_array.get(i).setNextRequest(reqeust_array.get(i + 1));
        }
        mRequestArray = reqeust_array;

        reqeust_array.get(0).start();
    }

    /*关闭请求链*/
    public void shutdown() {
        if (mRequestArray == null) return;
        for (IRequest request : mRequestArray) {
            request.shutdown();
        }
    }


}
