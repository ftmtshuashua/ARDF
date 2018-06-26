package com.lfp.ardf.util;

import java.util.ArrayList;

/**
 * 对象复用工具<br/>
 * <p>
 * Created by LiFuPing on 2018/6/26.
 *
 * @param <T> 复用对象
 */
public class ObjectReuseUtils<T> {
    final ArrayList<T> mScrapHeap = new ArrayList<>();

    public ObjectReuseUtils() {

    }

    public T obtain() {
        return mScrapHeap.isEmpty() ? null : mScrapHeap.remove(mScrapHeap.size() - 1);
    }

}
