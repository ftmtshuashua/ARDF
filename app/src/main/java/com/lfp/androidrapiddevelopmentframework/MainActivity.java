package com.lfp.androidrapiddevelopmentframework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.demo.DemoEntrance;
import com.lfp.androidrapiddevelopmentframework.demo.Demo_BaseRecyclerViewAdapter;
import com.lfp.androidrapiddevelopmentframework.demo.Demo_NetRequest;
import com.lfp.androidrapiddevelopmentframework.widget.WaitProgressBar;
import com.lfp.ardf.adapter.BaseRecyclerViewAdapter;
import com.lfp.ardf.adapter.SimpleRecyclerViewAdapter;
import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.util.ToastUtil;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;

/**
 * Demo列表
 * Created by LiFuPing on 2018/5/9.
 */
public class MainActivity extends BaseActivity {
    SimpleRecyclerViewAdapter<DemoEntrance> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBar =findViewById(R.id.view_WaitProgressBar);

        RecyclerView mRecyclerView = findViewById(R.id.view_ReyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter = new SimpleRecyclerViewAdapter(DemoEntranceHolder.class, R.layout.layout_simpler_textview));

        initListConfig();

        try {
            StatConfig.setDebugEnable(false);
            StatService.startStatService(getApplicationContext(), null, com.tencent.stat.common.StatConstants.VERSION);
        } catch (Exception e) {
            LogUtil.e(e);
        }


        LogUtil.d("hello %s", "world");
        LogUtil.d("debug");
        LogUtil.e("error");
        LogUtil.w("warning");
        LogUtil.v("verbose");
        LogUtil.i("information");
        LogUtil.wtf("What a Terrible Failure");

        LogUtil.json("[{\"Name\":\"小米\",\"Age\":18}]");
//        LogUtil.xml(XML_CONTENT);
    }

    /*Demo 配置*/
    void initListConfig() {
        List<DemoEntrance> arrays = new ArrayList<>();
        arrays.add(new Demo_BaseRecyclerViewAdapter.Demo(getAppFk()));
        arrays.add(new PlaceholderEntrance("框架核心-业务分离(验证名字，验证年龄，选择数据)"));
        arrays.add(new PlaceholderEntrance("地图城市选择器"));
        arrays.add(new PlaceholderEntrance("Dialog框架(BaseDialog)"));
        arrays.add(new PlaceholderEntrance("混淆配置(NotProguard)"));
        arrays.add(new PlaceholderEntrance("工具类说明"));
        arrays.add(new Demo_NetRequest.Demo(getAppFk()));
        arrays.add(mProgressDemo);

        mAdapter.setAndUpdata(arrays);
    }

    public static final class DemoEntranceHolder extends BaseRecyclerViewAdapter.BaseViewHolder<DemoEntrance> implements View.OnClickListener {
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
