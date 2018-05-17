package com.lfp.ardf.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自动创建Adapter，少写几行代码
 * Created by LiFuPing on 2018/5/11.
 */
public class SimpleRecyclerViewAdapter<D> extends BaseRecyclerViewAdapter<D> {

    Class<? extends BaseRecyclerViewAdapter.BaseViewHolder<D>> cls_vh;
    int layout_resouce_id;

    public SimpleRecyclerViewAdapter(Class<? extends BaseRecyclerViewAdapter.BaseViewHolder<D>> vh, int layout_resouce_id) {
        this.cls_vh = vh;
        this.layout_resouce_id = layout_resouce_id;
    }


    @NonNull
    @Override
    public BaseViewHolder<D> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            return cls_vh.getConstructor(View.class).newInstance(LayoutInflater.from(parent.getContext()).inflate(layout_resouce_id, parent, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
