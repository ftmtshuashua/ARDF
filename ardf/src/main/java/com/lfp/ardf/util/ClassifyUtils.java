package com.lfp.ardf.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * <pre>
 * desc:
 *      归类数据
 *
 * function:
 *      put()           :提交需要归类的数据
 *      getGroups()     :获得组列表
 *      getArray()      :通过组获得组数据
 *
 *
 * Created by LiFuPing on 2018/8/6.
 * </pre>
 */
public class ClassifyUtils<K, V> {
    transient HashMap<K, ArrayList<V>> mObjectData;

    /**
     * 提交需要归类的数据
     *
     * @param group 组名
     * @param data  数据
     */
    public void put(K group, V data) {
        ArrayList<V> al = mObjectData.get(group);
        if (al == null) mObjectData.put(group, al = new ArrayList<>());
        al.add(data);

    }

    /**
     * 获得组列表
     *
     * @return 获得组列表
     */
    public ArrayList<K> getGroups() {
        ArrayList<K> data = new ArrayList<>();
        Iterator<K> datas = mObjectData.keySet().iterator();
        while (datas.hasNext()) {
            data.add(datas.next());
        }
        return data;
    }

    /**
     * 通过组获得组数据
     *
     * @param group 组名
     * @return 组对应数据
     */
    public ArrayList<V> getArray(K group) {
        return mObjectData.get(group);
    }
}
