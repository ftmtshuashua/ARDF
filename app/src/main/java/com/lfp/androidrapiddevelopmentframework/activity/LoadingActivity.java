package com.lfp.androidrapiddevelopmentframework.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.activity.module.home.HomeActivity;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.util.PermissionManager;
import com.yanzhenjie.permission.Permission;

import java.util.List;

import lib.gg.y.Y_AdShow;

/**
 * 开屏页面<br>
 * Created by LiFuPing on 2018/5/31.
 */
public class LoadingActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFlag(FLAG_INTERCEPT_KEYCODE_BACK);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading);

//        if(true)return  ;

        new PermissionManager(getContext()) {
            @Override
            public void onFinish(List<String> data) {
                super.onFinish(data);
                new Y_AdShow(getAppFk())
                        .show((ViewGroup) findViewById(R.id.view_Splash), HomeActivity.class);
            }
        }.request(Permission.WRITE_EXTERNAL_STORAGE);


    }


    void next() {
        HomeActivity.start(getActivity());
        finish();
    }


}
