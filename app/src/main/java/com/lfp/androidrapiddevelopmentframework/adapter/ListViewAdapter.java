package com.lfp.androidrapiddevelopmentframework.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.lfp.androidrapiddevelopmentframework.adapter.viewholder.DemoEventViewHolder;
import com.lfp.androidrapiddevelopmentframework.adapter.viewholder.InfoEventViewHolder;
import com.lfp.androidrapiddevelopmentframework.adapter.viewholder.SwitchViewHolder;
import com.lfp.androidrapiddevelopmentframework.adapter.viewholder.SwitchViewModel;
import com.lfp.androidrapiddevelopmentframework.event.DemoEvent;
import com.lfp.androidrapiddevelopmentframework.event.InfoEvent;
import com.lfp.ardf.adapter.BaseRecyclerViewAdapter;

/**
 * <pre>
 * desc:
 *     listview adapter
 *
 * function:
 *
 * Created by LiFuPing on 2018/7/23.
 * </pre>
 */
public class ListViewAdapter extends BaseRecyclerViewAdapter {

    @Override
    public int getItemViewType(int position) {
        Object obj = getItemData(position);
        if (obj != null) {
            if (obj instanceof DemoEvent) return 3;
            if (obj instanceof SwitchViewModel) return 2;
            if (obj instanceof InfoEvent) return 1;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new InfoEventViewHolder(parent);
            case 2:
                return new SwitchViewHolder(parent);
            case 3:
                return new DemoEventViewHolder(parent);
        }
        return null;
    }


}
