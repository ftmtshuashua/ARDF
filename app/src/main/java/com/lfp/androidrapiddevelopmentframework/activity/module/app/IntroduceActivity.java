package com.lfp.androidrapiddevelopmentframework.activity.module.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.demo.DemoEntrance;
import com.lfp.ardf.framework.I.IAppFramework;

/**
 * 关于我们<br/>
 * Created by LiFuPing on 2018/5/31.
 */
public class IntroduceActivity extends BaseActivity {

    public static final void start(IAppFramework c) {
        Intent intent = new Intent(c.getContext(), IntroduceActivity.class);
        c.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);

    }


    /*Demo入口*/
    public static final class Demo extends DemoEntrance {
        IAppFramework appfk;

        public Demo(IAppFramework appfk) {
            super("关于ARDF");
            this.appfk = appfk;
        }

        @Override
        public void enter() {
            IntroduceActivity.start(appfk);
        }
    }

}
