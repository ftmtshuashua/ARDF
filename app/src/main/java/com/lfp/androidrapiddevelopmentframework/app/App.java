package com.lfp.androidrapiddevelopmentframework.app;

import android.app.Application;

import com.lfp.ardf.AppFrameworkHolper;
import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.util.CpuUtile;

/**
 * Created by LiFuPing on 2018/5/9.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppFrameworkHolper.init(getApplicationContext());

        LogUtil.e(CpuUtile.getCupInfo());

    }
}
