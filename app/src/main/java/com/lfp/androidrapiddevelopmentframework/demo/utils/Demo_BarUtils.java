package com.lfp.androidrapiddevelopmentframework.demo.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.adapter.ListViewAdapter;
import com.lfp.androidrapiddevelopmentframework.adapter.viewholder.SwitchViewModel;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.event.DemoEvent;
import com.lfp.androidrapiddevelopmentframework.event.InfoEvent;
import com.lfp.androidrapiddevelopmentframework.util.ActionBarControl;
import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.util.BarUtils;
import com.lfp.ardf.util.ColorUtils;
import com.lfp.ardf.util.ToastUtils;

import java.text.MessageFormat;

/**
 * <pre>
 * desc:
 *      BarUtils 工具说明
 * function:
 *
 * Created by LiFuPing on 2018/7/23.
 * </pre>
 */
public class Demo_BarUtils extends BaseActivity {

    ActionBarControl mActionBarControl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_barutils);
        mActionBarControl = new ActionBarControl(getActivity())
                .setTitle("BarUtils Demo")
                .setSubTitle("状态栏/导航栏/通知栏 相关工具")
                .showBack()
                .setBackFinishActivity(getActivity());


        RecyclerView mRecyclerView = findViewById(R.id.view_RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        ListViewAdapter mAdapter = new ListViewAdapter();
//        mAdapter.addData(new Test());

        mAdapter.addData(new GetStatusBarHeight());
        mAdapter.addData(new SwitchStateBarVisibility());
        mAdapter.addData(new SetStatusBarLightMode());
        mAdapter.addData(new SetStatusBarImmersiveView());
        mAdapter.addData(new SetStatusBarColor());

        mAdapter.addData(new GetActionBarHeight());
        mAdapter.addData(new ShowNotificationBar());

        mAdapter.addData(new GetNavigationBarHeight());
        mAdapter.addData(new SwitchNavigationBarVisibility());
        mAdapter.addData(new SetNavigationBarColor());
        mAdapter.addData(new SetNavigationBarImmersiveView());

        mRecyclerView.setAdapter(mAdapter);
    }


    /*-------------------------- 功能 -------------------------*/
    /*获得状态栏高度*/
    private final class GetStatusBarHeight extends InfoEvent {

        public GetStatusBarHeight() {
            super("获得状态栏高度");
        }

        @Override
        public void call() {
            ToastUtils.show(MessageFormat.format("状态栏高度:{0,number,0}", BarUtils.getStatusBarHeight()));
        }
    }

    /*改变状态栏可见性*/
    public final class SwitchStateBarVisibility extends SwitchViewModel {

        public SwitchStateBarVisibility() {
            super("隐藏状态栏");
        }

        @Override
        public void call() {
            boolean isVisibility = !getSwitchModel();
            setInfo(isVisibility ? "当前 - 状态栏可见" : "当前 - 状态栏不可见");
            BarUtils.setStatusBarVisibility(getWindow(), isVisibility);
        }

        @Override
        public void onClick() {
            ToastUtils.show(MessageFormat.format("当前 - 状态栏:{0}", BarUtils.isStatusBarVisible(getActivity()) ? "可见" : "不可见"));
        }
    }

    /*切换状态栏模式*/
    private final class SetStatusBarLightMode extends SwitchViewModel {

        public SetStatusBarLightMode() {
            super("设置高亮模式");
        }

        @Override
        public void call() {
            boolean isLight = getSwitchModel();
            setInfo(isLight ? "当前 - 高亮模式" : "当前 - 正常模式");
            BarUtils.setStatusBarLightMode(getActivity(), isLight);
        }

        @Override
        public void onClick() {
            ToastUtils.show(getSwitchModel() ? "当前 - 高亮模式" : "当前 - 正常模式");
        }
    }

    /*沉浸式状态栏*/
    private final class SetStatusBarImmersiveView extends InfoEvent {

        public SetStatusBarImmersiveView() {
            super("设置沉浸式状态栏");
        }

        @Override
        public void call() {
            BarUtils.setStatusBarImmersiveView(mActionBarControl.getRootView());
        }
    }

    /*设置状态栏颜色*/
    private final class SetStatusBarColor extends InfoEvent {

        public SetStatusBarColor() {
            super("设置状态栏颜色");
        }

        @Override
        public void call() {
            BarUtils.setStatusBarColor(getActivity(), ColorUtils.getRandomColor());
        }
    }

    /*获得系统ActionBar高度*/
    private final class GetActionBarHeight extends InfoEvent {

        public GetActionBarHeight() {
            super("获得系统ActionBar高度");
        }

        @Override
        public void call() {
            ToastUtils.show(MessageFormat.format("系统ActionBar高度:{0,number,0}", BarUtils.getActionBarHeight()));
        }
    }

    /*显示通知栏*/
    private final class ShowNotificationBar extends InfoEvent {

        public ShowNotificationBar() {
            super("显示通知栏");
        }

        @Override
        public void call() {
            BarUtils.setNotificationBarVisibility(true);
        }
    }

    /*获得导航栏高度*/
    private final class GetNavigationBarHeight extends InfoEvent {

        public GetNavigationBarHeight() {
            super("获得导航栏高度");
        }

        @Override
        public void call() {
            ToastUtils.show(MessageFormat.format("获得导航栏高度:{0,number,0}", BarUtils.getNavigationBarHeight()));
        }
    }

    /*改变导航栏可见性*/
    public final class SwitchNavigationBarVisibility extends SwitchViewModel {

        public SwitchNavigationBarVisibility() {
            super("改变导航栏可见性");
        }

        @Override
        public void call() {
            boolean model = !getSwitchModel();
            if (false == model && BarUtils.isNavigationBarVisible())
                ToastUtils.show("在底部边缘上滑动可以显示导航栏");
            BarUtils.setNavigationBarVisibility(getWindow(), model);
        }

        @Override
        public void onClick() {
            ToastUtils.show(MessageFormat.format("当前 - 导航栏:{0}", BarUtils.isNavigationBarVisible() ? "可见" : "不可见"));
        }
    }

    /*设置导航栏颜色*/
    private final class SetNavigationBarColor extends InfoEvent {

        public SetNavigationBarColor() {
            super("设置导航栏颜色");
        }

        @Override
        public void call() {
            BarUtils.setNavigationBarColor(getActivity(), ColorUtils.getRandomColor());
        }
    }

    /*沉浸式导航栏*/
    private final class SetNavigationBarImmersiveView extends InfoEvent {

        public SetNavigationBarImmersiveView() {
            super("沉浸式导航栏");
        }

        @Override
        public void call() {
            BarUtils.setNavigationBarImmersive(getActivity());
        }
    }

    /*-------------------------- 入口 -------------------------*/
    public static final class Demo extends DemoEvent {

        public Demo(IAppFramework appfk) {
            super(appfk, "BarUtils", "状态栏/导航栏/通知栏 相关工具");
        }

        @Override
        public void call() {
            Intent intent = new Intent(getAppFk().getContext(), Demo_BarUtils.class);
            getAppFk().startActivity(intent);
        }
    }

}
