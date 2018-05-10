package com.lfp.androidrapiddevelopmentframework.app;

import android.app.Application;

import com.lfp.ardf.AppFrameworkHolper;

/**
 * Created by LiFuPing on 2018/5/9.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppFrameworkHolper.init(getApplicationContext());

    }
}
