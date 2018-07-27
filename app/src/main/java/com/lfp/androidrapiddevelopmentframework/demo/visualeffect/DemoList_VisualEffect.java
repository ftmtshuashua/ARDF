package com.lfp.androidrapiddevelopmentframework.demo.visualeffect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.lfp.androidrapiddevelopmentframework.adapter.ListViewAdapter;
import com.lfp.androidrapiddevelopmentframework.base.BaseListActivity;
import com.lfp.androidrapiddevelopmentframework.dialog.ProgressDialog;
import com.lfp.androidrapiddevelopmentframework.event.DemoEvent;
import com.lfp.ardf.framework.I.IAppFramework;

/**
 * <pre>
 * desc:
 *      特效解决方案
 * function:
 *
 * Created by LiFuPing on 2018/7/26.
 * </pre>
 */
public class DemoList_VisualEffect extends BaseListActivity {


    @Override
    protected void onCreate(RecyclerView mRecyclerView) {
        getActionBarControl().setTitle("动效").setSubTitle("特效/动画解决方案");

        ListViewAdapter mAdapter = new ListViewAdapter();
        mAdapter.addData(new Demo_Shadow.Demo(getAppFk()));
        mAdapter.addData(new WaitBarModel(getAppFk()));
        mAdapter.addData(new Demo_ProgressBar.Demo(getAppFk()));
        mAdapter.addData(new Demo_Game.Demo(getAppFk()));

        mRecyclerView.setAdapter(mAdapter);
    }


    public static final class Demo extends DemoEvent {

        public Demo(IAppFramework appfk) {
            super(appfk, "动效", "特效/动画解决方案");
        }

        @Override
        public void call() {
            Intent intent = new Intent(getAppFk().getContext(), DemoList_VisualEffect.class);
            getAppFk().startActivity(intent);
        }
    }

    private static final class WaitBarModel extends DemoEvent {

        ProgressDialog mProgressDialog;

        public WaitBarModel(IAppFramework appfk) {
            super(appfk, "ProgressDialog", "等待对话框效果");
            mProgressDialog = new ProgressDialog(appfk.getAppFk()) {
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setCanceledOnTouchOutside(true);

                }
            };
            mProgressDialog.setCanceledOnTouchOutside(true);
        }

        @Override
        public void call() {
            mProgressDialog.show();
        }
    }

}
