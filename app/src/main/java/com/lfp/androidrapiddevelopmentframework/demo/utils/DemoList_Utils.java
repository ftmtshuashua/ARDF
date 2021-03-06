package com.lfp.androidrapiddevelopmentframework.demo.utils;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.lfp.androidrapiddevelopmentframework.adapter.ListViewAdapter;
import com.lfp.androidrapiddevelopmentframework.base.BaseListActivity;
import com.lfp.androidrapiddevelopmentframework.event.DemoEvent;
import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.util.BadgeUtils;
import com.lfp.ardf.util.ToastUtils;
import com.lfp.ardf.util.media.MediaUtils;

/**
 * <pre>
 * desc:
 *      工具类Demo列表
 *
 * function:
 *
 * Created by LiFuPing on 2018/7/24.
 * </pre>
 */
public class DemoList_Utils extends BaseListActivity {

    @Override
    protected void onCreate(RecyclerView mRecyclerView) {
        getActionBarControl().setTitle("工具类").setSubTitle("工具类使用方法说明");

        ListViewAdapter mAdapter = new ListViewAdapter();
        mAdapter.addData(new Demo_BarUtils.Demo(getAppFk()));
        mAdapter.addData(new BadgeUtilsEvent(getAppFk()));
        mAdapter.addData(new MediaUtilsEvent(getAppFk()));

        mRecyclerView.setAdapter(mAdapter);
    }


    private static final class BadgeUtilsEvent extends DemoEvent {

        public BadgeUtilsEvent(IAppFramework appfk) {
            super(appfk, "BadgeUtils", "在应用图标上显示角标");
        }

        @Override
        public void call() {
//          boolean is2=   ShortcutBadger.applyCount(AppUtils.createContext("com.ttc.lottery"), 7);
//            LogUtil.e(MessageFormat.format("设置另外一个程序的角标:{0}",is2));

            boolean is = BadgeUtils.setBadge(9);
            if (is) {
                ToastUtils.show("创建角标成功,请回到桌面查看效果.");
            } else {
                ToastUtils.show("创建角标失败,可能为手机不支持角标功能.");
            }
        }
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

    private static final class MediaUtilsEvent extends DemoEvent {

        public MediaUtilsEvent(IAppFramework appfk) {
            super(appfk, "MediaUtils", "媒体数据工具");
        }

        @Override
        public void call() {
            MediaUtils.test();
        }
    }

}
