package com.lfp.ardf.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;

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
            Constructor<BaseViewHolder<D>> cls = (Constructor<BaseViewHolder<D>>) cls_vh.getDeclaredConstructor(View.class);
            if (!cls.isAccessible()) cls.setAccessible(true);
            return cls.newInstance(LayoutInflater.from(parent.getContext()).inflate(layout_resouce_id, parent, false));
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException("ViewHolder构建错误，不要改变构造方法中的参数数量和参数类型，并且保证它不是一个非静态的内部类!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
