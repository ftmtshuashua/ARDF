package com.lfp.androidrapiddevelopmentframework.activity.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.base.BaseFragment;
import com.lfp.ardf.util.AssetsUtils;
import com.lfp.ardf.util.StatusBarUtils;

/**
 * 集成<br>
 * Created by LiFuPing on 2018/6/4.
 */
public class IntegrationFragment extends BaseFragment {

    public static final Fragment newInstance() {
        IntegrationFragment fragment = new IntegrationFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_intergration, null);
    }

    TextView mTV_Info;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StatusBarUtils.fitLayoutAtFullScreen(view.findViewById(R.id.layout_ActionBar), true);
        mTV_Info = view.findViewById(R.id.view_Info);

        mTV_Info.setText(AssetsUtils.getString("README.txt"));
    }
}
