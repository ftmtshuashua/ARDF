package com.lfp.androidrapiddevelopmentframework.demo.visualeffect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.event.DemoEvent;
import com.lfp.androidrapiddevelopmentframework.util.ActionBarControl;
import com.lfp.androidrapiddevelopmentframework.widget.WebProgressBar;
import com.lfp.ardf.framework.I.IAppFramework;

/**
 * <pre>
 * desc:
 *      进度条
 * function:
 *
 * Created by LiFuPing on 2018/7/26.
 * </pre>
 */
public class Demo_ProgressBar extends BaseActivity implements View.OnClickListener {

    WebProgressBar mWebProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_progressbar);
        new ActionBarControl(getActivity())
                .setfitsSystemWindows()
                .setTitle("进度条")
                .setSubTitle("进度条效果")
                .showBack()
                .setBackFinishActivity(getActivity())
        ;

        mWebProgressBar = findViewById(R.id.view_WebProgressBar);
        findViewById(R.id.view_Set).setOnClickListener(this);
        findViewById(R.id.view_Add).setOnClickListener(this);
        findViewById(R.id.view_Subtract).setOnClickListener(this);

        mWebProgressBar.setProgress(30);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_Set:
                mWebProgressBar.setProgress(10);
                break;
            case R.id.view_Add:
                mWebProgressBar.setProgress(mWebProgressBar.getProgress() + 20);
                break;
            case R.id.view_Subtract:
                mWebProgressBar.setProgress(mWebProgressBar.getProgress() - 20);
                break;
        }
    }


    public static final class Demo extends DemoEvent {

        public Demo(IAppFramework appfk) {
            super(appfk, "进度条", "进度条效果");
        }

        @Override
        public void call() {
            Intent intent = new Intent(getAppFk().getContext(), Demo_ProgressBar.class);
            getAppFk().startActivity(intent);
        }
    }

}
