package com.lfp.ardf.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView简化适配器
 * Created by LiFuPing on 2018/5/9.
 */
public abstract class BaseRecyclerViewAdapter<D> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder<D>> {

    /**
     * 禁用添加数据动画
     */
    public static final int FLAG_DISABLE_ITEM_ANIMATOR_ADD = 1;

    /**
     * 禁用插入数据动画
     */
    public static final int FLAG_DISABLE_ITEM_ANIMATOR_INSERT = 2;

    /**
     * 禁用移除数据动画
     */
    public static final int FLAG_DISABLE_ITEM_ANIMATOR_REMOVE = 4;

    /**
     * 禁用设置数据动画
     */
    public static final int FLAG_DISABLE_ITEM_ANIMATOR_SET = 8;

    /**
     * 禁用设置数据移动动画
     */
    public static final int FLAG_DISABLE_ITEM_ANIMATOR_MOVE = 16;

    /**
     * 禁用所有数据动画
     */
    public static final int FLAG_DISABLE_ITEM_ANIMATOR = 0xFF;

    /**
     * 用于列表中显示数据
     */
    final List<D> mArrayModel = new ArrayList<>();
    /**
     * 观察Adapter数据变化
     */
    OnAdapterDataChange mOnAdapterDataChange;
    ViewHolderMessageHandler mViewHolderMessageHandler;

    int mFlag;

    /**
     * 添加数据变化检查
     * @param l OnAdapterDataChange
     */
    public void setOnAdapterDataChange(OnAdapterDataChange l) {
        mOnAdapterDataChange = l;
    }

    /**
     * ViewHolder变化监听
     * @param l ViewHolderMessageHandler
     */
    public void setViewHolderMessageHandler(ViewHolderMessageHandler l) {
        mViewHolderMessageHandler = l;
    }

    public void setFlag(int flag) {
        mFlag |= flag;
    }

    /**
     * 播报数据更新
     */
    public void broadcastDataChange() {
        if (mOnAdapterDataChange != null) mOnAdapterDataChange.onChange(this);
    }

    /**
     * 获得列表数据
     * @return List
     */
    public List<D> getData() {
        return mArrayModel;
    }

    /**
     * 设置数据并且更新
     *
     * @param data 数据
     * @param <T> object
     */
    public <T extends D> void setAndUpdata(List<T> data) {
        int old_count = getItemCount();
        setData(data);
        int new_count = getItemCount();
        if ((mFlag & FLAG_DISABLE_ITEM_ANIMATOR_SET) != 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeRemoved(0, old_count);
            notifyItemRangeChanged(0, new_count);
        }
    }

    /**
     * 设置数据
     *
     * @param data 数据
     * @param <T> object
     */
    public <T extends D> void setData(List<T> data) {
        mArrayModel.clear();
        addData(data);
    }

    /**
     * 设置数据并且更新
     *
     * @param data 数据
     */
    public void setAndUpdata(D data) {
        int old_count = getItemCount();
        setData(data);
        int new_count = getItemCount();
        if ((mFlag & FLAG_DISABLE_ITEM_ANIMATOR_SET) != 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeRemoved(0, old_count);
            notifyItemRangeChanged(0, new_count);
        }
    }

    /**
     * 设置数据
     *
     * @param data 数据
     */
    public void setData(D data) {
        mArrayModel.clear();
        addData(data);
    }

    /**
     * 在末尾添加数据并更新
     *
     * @param data 数据
     * @param <T> object
     */
    public <T extends D> void addAndUpdata(List<T> data) {
        int old_count = getItemCount();
        addData(data);
        if ((mFlag & FLAG_DISABLE_ITEM_ANIMATOR_ADD) != 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeChanged(old_count, getItemCount());
        }
    }

    /**
     * 在末尾添加数据
     *
     * @param data 数据
     * @param <T> object
     */
    public <T extends D> void addData(List<T> data) {
        mArrayModel.addAll(data);
        broadcastDataChange();
    }

    /**
     * 在末尾添加数据并更新
     *
     * @param data 数据
     */
    public void addAndUpdata(D data) {
        int old_count = getItemCount();
        addData(data);
        if ((mFlag & FLAG_DISABLE_ITEM_ANIMATOR_ADD) != 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeChanged(old_count, getItemCount());
        }
    }

    /**
     * 在末尾添加数据
     *
     * @param data 数据
     */
    public void addData(D data) {
        mArrayModel.add(data);
        broadcastDataChange();
    }

    /**
     * 在index位置插入数据
     *
     * @param index 插入位置
     * @param data  数据
     * @param <T> object
     */
    public <T extends D> void insertAndUpdata(int index, List<T> data) {
        mArrayModel.addAll(index, data);
        broadcastDataChange();
        if ((mFlag & FLAG_DISABLE_ITEM_ANIMATOR_INSERT) != 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(index, data.size());
        }
    }

    /**
     * 在index位置插入单条数据
     *
     * @param index 插入位置
     * @param data  数据
     */
    public void insertAndUpdata(int index, D data) {
        mArrayModel.add(index, data);
        broadcastDataChange();
        if ((mFlag & FLAG_DISABLE_ITEM_ANIMATOR_INSERT) != 0) {
            notifyDataSetChanged();
        } else {
            notifyItemInserted(index);
        }
    }

    /**
     * 移除数据并更新
     *
     * @param postion 移除数据位置
     */
    public void removeAndUpdata(int postion) {
        removeData(postion);
        if ((mFlag & FLAG_DISABLE_ITEM_ANIMATOR_REMOVE) != 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRemoved(postion);
        }
    }

    /**
     * 移除数据
     *
     * @param postion 移除数据位置
     */
    public void removeData(int postion) {
        mArrayModel.remove(postion);
        broadcastDataChange();
    }

    /**
     * 移动数据
     *
     * @param fromPosition 被移动数据位置
     * @param toPosition   目标位置
     */
    public void moveAndUpdata(int fromPosition, int toPosition) {
        D form = mArrayModel.remove(fromPosition);
        mArrayModel.add(toPosition, form);
        broadcastDataChange();
        if ((mFlag & FLAG_DISABLE_ITEM_ANIMATOR_MOVE) != 0) {
            notifyDataSetChanged();
        } else {
            notifyItemMoved(fromPosition, toPosition);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<D> holder, int position) {
        D model = getItemData(position);
        holder.mSaveData = model;
        holder.mAdapter = this;
        holder.onUpdateUI(model);
    }

    /*获得下标对应位置数据*/
    public D getItemData(int postion) {
        return mArrayModel.get(postion);
    }

    @Override
    public int getItemCount() {
        return mArrayModel.size();
    }

    public static abstract class BaseViewHolder<D> extends RecyclerView.ViewHolder {
        D mSaveData;
        BaseRecyclerViewAdapter<D> mAdapter;

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public Context getContext() {
            return itemView.getContext();
        }

        protected BaseRecyclerViewAdapter<D> getAdapter() {
            return mAdapter;
        }

        /**
         * 获取当前UI对应的数据
         * @return  object
         */
        public D getSaveData() {
            return mSaveData;
        }

        /**
         * 更新UI
         *
         * @param data object
         */
        public abstract void onUpdateUI(D data);

        /**
         * 发送一个消息给Adapter的创建者，创建者调用Adapter的setViewHolderMessageHandler()方法接收消息
         *
         * @param what 消息类型
         * @param obj  消息中携带数据
         */
        protected void sendMessage(int what, Object obj) {
            ViewHolderMessageHandler l = mAdapter.mViewHolderMessageHandler;
            if (l != null) {
                l.handlerAdapterMessage(what, obj);
            } else new NullPointerException("未设置ViewHolderMessageHandler!");
        }
    }

    /**
     * 监听适配器的数据变化
     */
    public interface OnAdapterDataChange<D> {
        void onChange(BaseRecyclerViewAdapter<D> adapter);
    }

    /**
     * ViewHolder变化监听
     */
    public interface ViewHolderMessageHandler<E> {

        /**
         * @param what 消息类型
         * @param obj  消息中携带数据
         */
        void handlerAdapterMessage(int what, E obj);

    }

}
