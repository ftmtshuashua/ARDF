package com.lfp.ardf.util;

import java.util.LinkedList;

/**
 * <pre>
 * desc:
 *      线程安全的实例复用工具
 *
 * function:
 *      obtain()    :获得一个实例
 *      recycle()   :回收实例
 *      create()    :创建实例
 *
 * Created by LiFuPing on 2018/6/26.
 *
 * @param <T> 对象类型
 *
 * </pre>
 */
public abstract class ObjectReuseUtils<T> {
    final Object sPoolSync = new Object();
    final LinkedList<T> mScrapHeap = new LinkedList<>();

    public ObjectReuseUtils() {

    }

    /**
     * 如果缓存中存在对象,则从缓存获取.否则创建一个新对象
     *
     * @return 对象实例
     */
    public T obtain() {
        T obj;
        synchronized (sPoolSync) {
            if (!mScrapHeap.isEmpty()) {
                obj = mScrapHeap.removeFirst();
            } else {
                obj = create();
            }
        }
        return obj;
    }

    /**
     * 回收实例,请确保对象应该被回收
     *
     * @param obj 被回收的对象
     */
    public void recycle(T obj) {
        synchronized (sPoolSync) {
            mScrapHeap.add(obj);
        }
    }

    /**
     * 当未缓存任何对象的时候,会回调此方法来创建一个新的实例
     *
     * @return 对象实例
     */
    public abstract T create();


}
