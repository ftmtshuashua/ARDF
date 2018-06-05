package com.lfp.androidrapiddevelopmentframework.util;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.ardf.util.ViewUtil;

/**
 * 自定义ActionBar控制器<br/>
 * 控制 R.layout.layout_actionbar 布局控制器
 * <p>
 * Created by LiFuPing on 2018/6/5.
 */
public class ActionBarControl {
    public static final int FLAG_SHOW_BACK = 0x1;
    public static final int FLAG_SHOW_FINISH = 0x1 << 1;
    public static final int FLAG_SHOW_TITLE = 0x1 << 2;
    public static final int FLAG_SHOW_SUBTITLe = 0x1 << 3;

    public static final int FLAG_SHOW_LEFT_CONTROL = FLAG_SHOW_BACK | FLAG_SHOW_FINISH;

    View mV_ActionBar;
    View mV_LeftControl, mV_Back, mV_Finish;
    TextView mTV_Title, mTV_SubTitle;

    int flag;

    public ActionBarControl(Activity activity) {
        this(activity.getWindow().getDecorView());
    }

    public ActionBarControl(View v) {
        mV_ActionBar = v.findViewById(R.id.layout_ActionBar);
        if (!hasActionBar()) return;
        mV_LeftControl = v.findViewById(R.id.layout_LeftControl);
        mV_Back = v.findViewById(R.id.view_Back);
        mV_Finish = v.findViewById(R.id.view_Finish);
        mTV_Title = v.findViewById(R.id.view_Title);
        mTV_SubTitle = v.findViewById(R.id.view_SubTitle);
    }

    public boolean hasActionBar() {
        return mV_ActionBar != null;
    }

    public void setFlag(int flag) {
        this.flag |= flag;
    }

    public ActionBarControl showBack() {
        setFlag(FLAG_SHOW_BACK);
        return this;
    }

    public ActionBarControl showFinish() {
        setFlag(FLAG_SHOW_FINISH);
        return this;
    }

    public ActionBarControl showTitle() {
        setFlag(FLAG_SHOW_TITLE);
        return this;
    }

    public ActionBarControl showSubTitle() {
        setFlag(FLAG_SHOW_SUBTITLe);
        return this;
    }

    public void notifyChange() {
        ViewUtil.setVisibility(mV_LeftControl, (flag & FLAG_SHOW_LEFT_CONTROL) == 0 ? View.GONE : View.VISIBLE);
        ViewUtil.setVisibility(mV_Back, (flag & FLAG_SHOW_BACK) == 0 ? View.GONE : View.VISIBLE);
        ViewUtil.setVisibility(mV_Finish, (flag & FLAG_SHOW_FINISH) == 0 ? View.GONE : View.VISIBLE);
        ViewUtil.setVisibility(mTV_Title, (flag & FLAG_SHOW_TITLE) == 0 ? View.GONE : View.VISIBLE);
        ViewUtil.setVisibility(mTV_SubTitle, (flag & FLAG_SHOW_SUBTITLe) == 0 ? View.GONE : View.VISIBLE);
    }
}
