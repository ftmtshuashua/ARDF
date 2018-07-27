package com.lfp.androidrapiddevelopmentframework.demo.visualeffect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.event.DemoEvent;
import com.lfp.androidrapiddevelopmentframework.util.ActionBarControl;
import com.lfp.ardf.framework.I.IAppFramework;

/**
 * 阴影<br>
 * Created by LiFuPing on 2018/6/20.
 */
public class Demo_Shadow extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow);

        new ActionBarControl(getActivity())
                .setTitle("ShadowView")
                .setSubTitle("布局阴影效果解决方案")
                .showBack()
                .setBackFinishActivity(getActivity());

    }


    public static final class Demo extends DemoEvent {

        public Demo(IAppFramework appfk) {
            super(appfk, "ShadowView", "布局阴影效果解决方案");
        }

        @Override
        public void call() {
            Intent intent = new Intent(getAppFk().getContext(), Demo_Shadow.class);
            getAppFk().startActivity(intent);
        }
    }

}
