package com.lfp.androidrapiddevelopmentframework.activity.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.activity.module.app.IntroduceActivity;
import com.lfp.androidrapiddevelopmentframework.base.BaseFragment;
import com.lfp.androidrapiddevelopmentframework.demo.DemoEntrance;
import com.lfp.androidrapiddevelopmentframework.demo.Demo_BaseRecyclerViewAdapter;
import com.lfp.androidrapiddevelopmentframework.demo.Demo_NetRequest;
import com.lfp.androidrapiddevelopmentframework.widget.WaitProgressBar;
import com.lfp.ardf.adapter.SimpleRecyclerViewAdapter;
import com.lfp.ardf.util.StatusBarUtil;
import com.lfp.ardf.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>
 * Created by LiFuPing on 2018/6/1.
 */
public class DemoFragment extends BaseFragment {


    public static Fragment newInstance() {
        DemoFragment fragment = new DemoFragment();

        return fragment;
    }

    SimpleRecyclerViewAdapter<DemoEntrance> mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.activity_main, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBar = view.findViewById(R.id.view_WaitProgressBar);

        RecyclerView mRecyclerView = view.findViewById(R.id.view_ReyclerView);
        StatusBarUtil.fitLayoutAtFullScreen(mRecyclerView, false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter = new SimpleRecyclerViewAdapter(DemoEntranceHolder.class, R.layout.layout_simpler_textview));

        initListConfig();
    }

    /*Demo 配置*/
    void initListConfig() {
        List<DemoEntrance> arrays = new ArrayList<>();
        arrays.add(new IntroduceActivity.Demo(getAppFk()));
        arrays.add(new Demo_BaseRecyclerViewAdapter.Demo(getAppFk()));
//        arrays.add(new PlaceholderEntrance("框架核心-业务分离(验证名字，验证年龄，选择数据)"));
//        arrays.add(new PlaceholderEntrance("地图城市选择器"));
//        arrays.add(new PlaceholderEntrance("Dialog框架(BaseDialog)"));
//        arrays.add(new PlaceholderEntrance("混淆配置(NotProguard)"));
//        arrays.add(new PlaceholderEntrance("工具类说明"));
        arrays.add(new Demo_NetRequest.Demo(getAppFk()));
        arrays.add(mProgressDemo);

        mAdapter.setAndUpdata(arrays);
    }

    public static final class DemoEntranceHolder extends SimpleRecyclerViewAdapter.NotProguardPViewHolder<DemoEntrance> implements View.OnClickListener {
        TextView mTV_Info;

        public DemoEntranceHolder(View itemView) {
            super(itemView);
            mTV_Info = itemView.findViewById(R.id.view_Info);
            mTV_Info.setOnClickListener(this);
        }

        @Override
        public void onUpdateUI(DemoEntrance data) {
            mTV_Info.setText(data.getInfo());
        }

        @Override
        public void onClick(View v) {
            getSaveData().enter();
        }
    }

    /*占位*/
    private static final class PlaceholderEntrance extends DemoEntrance {

        public PlaceholderEntrance(String info) {
            super(info);
        }

        @Override
        public void enter() {
            ToastUtil.show(getInfo());
        }
    }

    WaitProgressBar mBar;

    DemoEntrance mProgressDemo = new DemoEntrance("测试 ProgressBar") {
        @Override
        public void enter() {
            mBar.setVisibility(mBar.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        }
    };

}
