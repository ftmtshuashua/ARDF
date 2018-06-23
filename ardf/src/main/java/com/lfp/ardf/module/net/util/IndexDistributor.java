package com.lfp.ardf.module.net.util;

import com.lfp.ardf.module.net.i.RequestNode;

import java.util.Iterator;

/**
 * Index分配器<br>
 * <p>
 * 用来保证请求链中每个请求的位置是固定当
 * <p>
 * Created by LiFuPing on 2018/6/14.
 */
public class IndexDistributor {

    /**
     * 分配下级节点的位置
     *
     * @param reqeust_array 下级节点
     * @param index_begin   起点位置
     */
    public static void distribution(Iterable<RequestNode> reqeust_array, int index_begin) {
        if (reqeust_array == null) return;
        Iterator<RequestNode> iterator = reqeust_array.iterator();
        int index = index_begin;
        while (iterator.hasNext()) {
            RequestNode request = iterator.next();
            if (request != null) {
                request.setIndex(index);
                index += request.length();
            } else {
                index += 1;
            }
        }
    }

    /**
     * 获得下级节点的总长度
     *
     * @param reqeust_array 下级节点
     * @return 节点总长度
     */
    public static int nodeLenth(Iterable<RequestNode> reqeust_array) {
        int count = 0;
        if (reqeust_array == null) return count;
        Iterator<RequestNode> iterator = reqeust_array.iterator();
        while (iterator.hasNext()) {
            RequestNode request = iterator.next();
            if (request == null) count++;
            else count += request.length();
        }
        return count;
    }

}
