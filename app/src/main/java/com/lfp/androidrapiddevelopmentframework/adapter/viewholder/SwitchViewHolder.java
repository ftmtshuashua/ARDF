package com.lfp.androidrapiddevelopmentframework.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.ardf.adapter.BaseRecyclerViewAdapter;
import com.lfp.ardf.util.Utils;

/**
 * <pre>
 * desc:
 *      状态切换ViewHolder
 * function:
 *
 * Created by LiFuPing on 2018/7/23.
 * </pre>
 */
public class SwitchViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder<SwitchViewModel> implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    TextView mTV_Info;
    Switch mV_Switch;

    public SwitchViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_simpler_switch, parent, false));
        mTV_Info = itemView.findViewById(R.id.view_Info);
        mV_Switch = itemView.findViewById(R.id.view_Switch);
        mTV_Info.setOnClickListener(this);
        mV_Switch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onUpdateUI(SwitchViewModel data) {
        mTV_Info.setText(Utils.getNotNull(data.getInfo()));
        mV_Switch.setChecked(data.getSwitchModel());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked != getSaveData().getSwitchModel()) {
            getSaveData().setSwitchModel(isChecked);
            getSaveData().call();
            onUpdateUI(getSaveData());
        }
    }


    @Override
    public void onClick(View v) {
        getSaveData().onClick();
    }
}
