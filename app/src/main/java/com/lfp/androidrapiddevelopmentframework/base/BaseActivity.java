package com.lfp.androidrapiddevelopmentframework.base;

import android.view.View;
import android.view.ViewGroup;

import com.lfp.androidrapiddevelopmentframework.api.Apiserver;
import com.lfp.ardf.framework.imp.activity.ImpActivity;
import com.tencent.stat.StatService;

/**
 * Created by LiFuPing on 2018/5/11.
 */
public class BaseActivity extends ImpActivity {

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    /*获得API配置*/
    protected Apiserver getApiserver() {
        return Apiserver.getInstace();
    }

}
