package com.lfp.androidrapiddevelopmentframework.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.event.DemoEvent;
import com.lfp.ardf.adapter.BaseRecyclerViewAdapter;
import com.lfp.ardf.util.Utils;
import com.lfp.ardf.util.ViewUtils;

/**
 * <pre>
 * desc:
 *      DemoEventViewHolder
 * function:
 *
 * Created by LiFuPing on 2018/7/23.
 * </pre>
 */
public class DemoEventViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder<DemoEvent> implements View.OnClickListener {
    TextView mTV_Info, mTV_Title;

    public DemoEventViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tvtitle_tvinfo, parent, false));
        itemView.findViewById(R.id.layout_Root).setOnClickListener(this);
        mTV_Title = itemView.findViewById(R.id.view_Title);
        mTV_Info = itemView.findViewById(R.id.view_Info);
    }

    @Override
    public void onUpdateUI(DemoEvent data) {
        mTV_Title.setText(data.getTitle());
        boolean hasInfo = !Utils.isEmpty(data.getInfo());
        if (hasInfo) mTV_Info.setText(data.getInfo());
        ViewUtils.setVisibility(mTV_Info, hasInfo ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onClick(View v) {
        getSaveData().call();
    }
}
