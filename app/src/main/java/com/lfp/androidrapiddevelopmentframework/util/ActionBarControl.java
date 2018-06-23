package com.lfp.androidrapiddevelopmentframework.util;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.ardf.util.StatusBarUtil;
import com.lfp.ardf.util.ViewUtil;

/**
 * 自定义ActionBar控制器<br>
 * 控制 R.layout.layout_actionbar 布局控制器
 * <p>
 * Created by LiFuPing on 2018/6/5.
 */
public class ActionBarControl {
    public static final int FLAG_SHOW_BACK = 0x1;
    public static final int FLAG_SHOW_FINISH = 0x1 << 1;
    public static final int FLAG_SHOW_TITLE = 0x1 << 2;
    public static final int FLAG_SHOW_SUBTITLE = 0x1 << 3;
    public static final int FLAG_SHOW_LEFT_CONTROL = FLAG_SHOW_BACK | FLAG_SHOW_FINISH;
    public static final int FLAG_SHOW_MASK = 0xFFFF; //Integer 32 bit

    View mV_ActionBar;
    View mV_LeftControl, mV_Back;
    TextView mTV_Finish;
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
        mTV_Finish = v.findViewById(R.id.view_Finish);
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
        notifyChange();
        return this;
    }

    public ActionBarControl hiddenBack() {
        flag &= ~FLAG_SHOW_BACK;
        notifyChange();
        return this;
    }

    public ActionBarControl showFinish() {
        setFlag(FLAG_SHOW_FINISH);
        notifyChange();
        return this;
    }

    public void notifyChange() {
        if (!hasActionBar()) return;
        ViewUtil.setVisibility(mV_ActionBar, (flag & FLAG_SHOW_MASK) == 0 ? View.GONE : View.VISIBLE);
        ViewUtil.setVisibility(mV_LeftControl, (flag & FLAG_SHOW_LEFT_CONTROL) == 0 ? View.GONE : View.VISIBLE);
        ViewUtil.setVisibility(mV_Back, (flag & FLAG_SHOW_BACK) == 0 ? View.GONE : View.VISIBLE);
        ViewUtil.setVisibility(mTV_Finish, (flag & FLAG_SHOW_FINISH) == 0 ? View.GONE : View.VISIBLE);
        ViewUtil.setVisibility(mTV_Title, (flag & FLAG_SHOW_TITLE) == 0 ? View.GONE : View.VISIBLE);
        ViewUtil.setVisibility(mTV_SubTitle, (flag & FLAG_SHOW_SUBTITLE) == 0 ? View.GONE : View.VISIBLE);
    }

    public ActionBarControl setfitsSystemWindows() {
        if (!hasActionBar()) return this;
        StatusBarUtil.fitLayoutAtFullScreen(mV_ActionBar, true);
        return this;
    }

    public final int getBackViewId() {
        if (!hasActionBar()) return 0;
        return mV_Back.getId();
    }

    public int getFinishViewId() {
        if (!hasActionBar()) return 0;
        return mTV_Finish.getId();
    }

    public int getTitleViewId() {
        if (!hasActionBar()) return 0;
        return mTV_Title.getId();
    }

    public int getSubTitleViewId() {
        if (!hasActionBar()) return 0;
        return mTV_SubTitle.getId();
    }

    public ActionBarControl setTitle(CharSequence title) {
        if (!hasActionBar()) return this;
        setFlag(FLAG_SHOW_TITLE);
        mTV_Title.setText(title);
        notifyChange();
        return this;
    }

    public ActionBarControl setSubTitle(CharSequence subtitle) {
        if (!hasActionBar()) return this;
        setFlag(FLAG_SHOW_SUBTITLE);
        mTV_SubTitle.setText(subtitle);
        notifyChange();
        return this;
    }

    public ActionBarControl setFinish(CharSequence subtitle) {
        if (!hasActionBar()) return this;
        setFlag(FLAG_SHOW_SUBTITLE);
        mTV_Finish.setText(subtitle);
        notifyChange();
        return this;
    }


    public ActionBarControl setTitleOnClick(View.OnClickListener onClick) {
        if (!hasActionBar()) return this;
        mTV_Title.setOnClickListener(onClick);
        return this;
    }

    public ActionBarControl setBackOnClick(View.OnClickListener onClick) {
        if (!hasActionBar()) return this;
        mV_Back.setOnClickListener(onClick);
        return this;
    }

    public ActionBarControl setFinishOnClick(View.OnClickListener onClick) {
        if (!hasActionBar()) return this;
        mTV_Finish.setOnClickListener(onClick);
        return this;
    }

    public ActionBarControl setBackFinishActivity(final Activity activity) {
        if (!hasActionBar()) return this;
        mV_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        return this;
    }

}
