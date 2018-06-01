package com.lfp.androidrapiddevelopmentframework.activity.module.home.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.base.BaseFragment;
import com.lfp.ardf.debug.LogUtil;

import java.text.MessageFormat;

/**
 * <br/>
 * Created by LiFuPing on 2018/6/1.
 */
public class TestFragment extends BaseFragment {

    String tag;

    public TestFragment() {

    }

    @SuppressLint("ValidFragment")
    public TestFragment(String tag) {
        this.tag = tag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e(MessageFormat.format("onCreate() - {0}", tag));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView TV = new TextView(getContext());
        TV.setGravity(Gravity.CENTER);
        TV.setTextSize(30);
        TV.setText(tag);
        return TV;
    }

    @Override
    public void onFragmentShow() {
        super.onFragmentShow();
        LogUtil.e(MessageFormat.format("onFragmentShow() - {0}", tag));
    }
}
