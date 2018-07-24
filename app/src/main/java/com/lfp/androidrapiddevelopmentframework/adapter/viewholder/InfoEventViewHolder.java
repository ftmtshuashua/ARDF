package com.lfp.androidrapiddevelopmentframework.adapter.viewholder;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.event.InfoEvent;
import com.lfp.ardf.adapter.BaseRecyclerViewAdapter;
import com.lfp.ardf.util.Utils;

/**
 * <pre>
 * desc:
 *      InfoEvent view adapter
 *
 * function:
 *
 * Created by LiFuPing on 2018/7/23.
 * </pre>
 */
public class InfoEventViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder<InfoEvent> implements View.OnClickListener {
    TextView mTV_Info;

    public InfoEventViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_simpler_textview, parent, false));
        mTV_Info = itemView.findViewById(R.id.view_Info);
        mTV_Info.setOnClickListener(this);
    }

    @Override
    public void onUpdateUI(InfoEvent data) {
        mTV_Info.setText(Utils.getNotNull(data.getInfo()));
    }

    @Override
    public void onClick(View v) {
        getSaveData().call();
    }
}

