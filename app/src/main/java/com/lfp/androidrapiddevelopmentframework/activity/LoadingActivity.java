package com.lfp.androidrapiddevelopmentframework.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.activity.module.home.HomeActivity;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.util.PermissonManager;
import com.lfp.ardf.debug.LogUtil;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;
import com.yanzhenjie.permission.Permission;

import java.util.List;

import lib.gg.y.Y_AdShow;

/**
 * 开屏页面<br/>
 * Created by LiFuPing on 2018/5/31.
 */
public class LoadingActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading);

//        if(true)return  ;
        try {
            StatConfig.setDebugEnable(false);
            StatService.startStatService(getApplicationContext(), null, com.tencent.stat.common.StatConstants.VERSION);
        } catch (Exception e) {
            LogUtil.e(e);
        }

        new PermissonManager(getContext()) {
            @Override
            public void onFinish(List<String> data) {
                super.onFinish(data);
                new Y_AdShow(getAppFk())
                        .show((ViewGroup) findViewById(R.id.view_Splash), HomeActivity.class);
            }
        }.request(Permission.WRITE_EXTERNAL_STORAGE);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    void next() {
        HomeActivity.start(getActivity());
        finish();
    }


}
