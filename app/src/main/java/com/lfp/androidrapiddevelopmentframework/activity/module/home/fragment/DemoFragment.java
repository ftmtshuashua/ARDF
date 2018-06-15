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
import com.lfp.androidrapiddevelopmentframework.base.BaseFragment;
import com.lfp.androidrapiddevelopmentframework.demo.Demo_BaseRecyclerViewAdapter;
import com.lfp.androidrapiddevelopmentframework.demo.Demo_NetRequest;
import com.lfp.androidrapiddevelopmentframework.demo.Demo_RadioGroupControl;
import com.lfp.androidrapiddevelopmentframework.demo.Demo_WebActivity;
import com.lfp.androidrapiddevelopmentframework.event.DemoEvent;
import com.lfp.androidrapiddevelopmentframework.util.ActionBarControl;
import com.lfp.ardf.adapter.SimpleRecyclerViewAdapter;
import com.lfp.ardf.util.ToastUtil;
import com.lfp.ardf.util.Utils;
import com.lfp.ardf.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Demo 展示列表 <br/>
 * Created by LiFuPing on 2018/6/1.
 */
public class DemoFragment extends BaseFragment {

    public static Fragment newInstance() {
        DemoFragment fragment = new DemoFragment();

        return fragment;
    }

    SimpleRecyclerViewAdapter<DemoEvent> mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.activity_main, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new ActionBarControl(view).setfitsSystemWindows()
                .setTitle("Demo")
                .setSubTitle("Demo 列表");


        RecyclerView mRecyclerView = view.findViewById(R.id.view_ReyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter = new SimpleRecyclerViewAdapter(DemoEntranceHolder.class, R.layout.layout_tvtitle_tvinfo));

        initListConfig(view);
    }

    /*Demo 配置*/
    void initListConfig(View view) {
        List<DemoEvent> arrays = new ArrayList<>();
        arrays.add(new Demo_RadioGroupControl.Demo(getAppFk()));
        arrays.add(new ToastControlEvent("FragmentControl", "请参考HomeActivity"));
        arrays.add(new Demo_BaseRecyclerViewAdapter.Demo(getAppFk()));
//        arrays.add(new PlaceholderEntrance("框架核心-业务分离(验证名字，验证年龄，选择数据)"));
//        arrays.add(new PlaceholderEntrance("地图城市选择器"));
//        arrays.add(new PlaceholderEntrance("Dialog框架(BaseDialog)"));
//        arrays.add(new PlaceholderEntrance("混淆配置(NotProguard)"));
//        arrays.add(new PlaceholderEntrance("工具类说明"));
        arrays.add(new Demo_NetRequest.Demo(getAppFk()));
        arrays.add(new VisibilityControlEvent(view.findViewById(R.id.view_WaitProgressBar), "BaseProgressBarView", "ProgressBar解决方案"));
        arrays.add(new Demo_WebActivity.Demo(getAppFk()));

        mAdapter.setAndUpdata(arrays);
    }

    public static final class DemoEntranceHolder extends SimpleRecyclerViewAdapter.NotProguardViewHolder<DemoEvent> implements View.OnClickListener {
        TextView mTV_Info, mTV_Title;

        public DemoEntranceHolder(View itemView) {
            super(itemView);
            itemView.findViewById(R.id.layout_Root).setOnClickListener(this);
            mTV_Title = itemView.findViewById(R.id.view_Title);
            mTV_Info = itemView.findViewById(R.id.view_Info);
        }

        @Override
        public void onUpdateUI(DemoEvent data) {
            mTV_Title.setText(data.getTitle());
            boolean hasInfo = !Utils.isEmpty(data.getInfo());
            if (hasInfo) mTV_Info.setText(data.getInfo());
            ViewUtil.setVisibility(mTV_Info, hasInfo ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            getSaveData().call();
        }
    }

    /*控件显示控制*/
    private static final class VisibilityControlEvent extends DemoEvent {
        View mView;

        public VisibilityControlEvent(View view, String title) {
            super(null, title);
            mView = view;
        }

        public VisibilityControlEvent(View view, String title, String info) {
            super(null, title);
            mView = view;
            setInfo(info);
        }

        @Override
        public void call() {
            ViewUtil.setVisibilitySwitch(mView);
        }
    }

    /*Toast消息*/
    private static final class ToastControlEvent extends DemoEvent {

        public ToastControlEvent(String title) {
            super(null, title);
        }

        public ToastControlEvent(String title, String info) {
            super(null, title);
            setInfo(info);
        }

        @Override
        public void call() {
            if (!Utils.isEmpty(getInfo())) ToastUtil.show(getInfo());
        }
    }

}
