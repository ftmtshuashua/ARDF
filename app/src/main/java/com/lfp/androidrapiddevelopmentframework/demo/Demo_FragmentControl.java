package com.lfp.androidrapiddevelopmentframework.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.event.DemoEvent;
import com.lfp.androidrapiddevelopmentframework.util.ActionBarControl;
import com.lfp.ardf.framework.I.IAppFramework;

/**
 * FragmentControl demo<br/>
 * Created by LiFuPing on 2018/6/6.
 */
public class Demo_FragmentControl extends BaseActivity {

    public static final void start(IAppFramework appfk) {
        Intent intent = new Intent(appfk.getContext(), Demo_FragmentControl.class);
        appfk.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_fragmentcontrol);
        new ActionBarControl(getActivity())
                .showBack().setBackFinishActivity(getActivity())
                .setTitle("FragmentControl Demo");


    }

    public static final class Demo extends DemoEvent {

        public Demo(IAppFramework appfk) {
            super(appfk, "FragmentControl");
            setInfo("Fragment 切换控制器");
        }

        @Override
        public void call() {
            Demo_FragmentControl.start(getAppFk());
        }
    }

}
