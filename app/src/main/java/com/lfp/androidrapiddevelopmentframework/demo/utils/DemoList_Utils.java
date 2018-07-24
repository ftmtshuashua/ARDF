package com.lfp.androidrapiddevelopmentframework.demo.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.adapter.ListViewAdapter;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.event.DemoEvent;
import com.lfp.androidrapiddevelopmentframework.util.ActionBarControl;
import com.lfp.ardf.framework.I.IAppFramework;

/**
 * <pre>
 * desc:
 *      工具类Demo列表
 * function:
 *
 * Created by LiFuPing on 2018/7/24.
 * </pre>
 */
public class DemoList_Utils extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycerview);
        new ActionBarControl(getActivity())
                .setfitsSystemWindows()
                .setTitle("工具类")
                .setSubTitle("工具类使用方法说明")
                .showBack()
                .setBackFinishActivity(getActivity())
        ;

        RecyclerView mRecyclerView = findViewById(R.id.view_RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


        ListViewAdapter mAdapter = new ListViewAdapter();
        mAdapter.addData(new Demo_BarUtils.Demo(getAppFk()));

        mRecyclerView.setAdapter(mAdapter);

    }


    public static final class Demo extends DemoEvent {

        public Demo(IAppFramework appfk) {
            super(appfk, "工具类", "工具类使用方法说明");
        }

        @Override
        public void call() {
            Intent intent = new Intent(getAppFk().getContext(), DemoList_Utils.class);
            getAppFk().startActivity(intent);
        }
    }

}
