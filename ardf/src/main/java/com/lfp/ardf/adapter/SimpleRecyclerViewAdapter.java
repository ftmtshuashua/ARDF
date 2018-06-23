package com.lfp.ardf.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.model.NotProguard;

import java.lang.reflect.Constructor;
import java.text.MessageFormat;

/**
 * 自动创建Adapter，少写几行代码
 * 推荐使用 BaseRecyclerViewAdapter自己实现
 * Created by LiFuPing on 2018/5/11.
 */
public class SimpleRecyclerViewAdapter<D> extends BaseRecyclerViewAdapter<D> {

    Class<? extends NotProguardViewHolder<D>> cls_vh;
    int layout_resouce_id;

    public SimpleRecyclerViewAdapter(Class<? extends NotProguardViewHolder<D>> vh, int layout_resouce_id) {
        this.cls_vh = vh;
        this.layout_resouce_id = layout_resouce_id;
    }

    @NonNull
    @Override
    public BaseViewHolder<D> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            Constructor<NotProguardViewHolder<D>> cls = (Constructor<NotProguardViewHolder<D>>) cls_vh.getDeclaredConstructor(View.class);
            if (!cls.isAccessible()) cls.setAccessible(true);
            return cls.newInstance(LayoutInflater.from(parent.getContext()).inflate(layout_resouce_id, parent, false));
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException("ViewHolder构建错误，不要改变构造方法中的参数数量和参数类型，并且保证它不是一个非静态的内部类!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用反射创建ViewHolder必须保证它不被混淆
     * 推荐使用 BaseRecyclerViewAdapter自己实现
     *
     * @param <T> object
     */
    public static abstract class NotProguardViewHolder<T> extends BaseRecyclerViewAdapter.BaseViewHolder<T> implements NotProguard {

        public NotProguardViewHolder(View itemView) {
            super(itemView);
        }
    }

}
