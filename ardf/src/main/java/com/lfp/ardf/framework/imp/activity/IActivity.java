package com.lfp.ardf.framework.imp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.framework.I.ILifeCycleObserve;
import com.lfp.ardf.framework.LifeCycleObservedUtil;

/**
 * <pre>
 * desc:
 *      核心框架
 * function:
 *
 * Created by LiFuPing on 2018/5/11.
 * </pre>
 */
 class IActivity extends AppCompatActivity implements IAppFramework {
    LifeCycleObservedUtil mLifeCycleObservedUtil = new LifeCycleObservedUtil();

    @Override
    public FragmentManager getSmartFragmentManager() {
        return getSupportFragmentManager();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public IAppFramework getAppFk() {
        return this;
    }


    @Override
    public void registeredObserve(ILifeCycleObserve l) {
        mLifeCycleObservedUtil.registeredObserve(l);
    }

    @Override
    public void unRegisteredObserve(ILifeCycleObserve l) {
        mLifeCycleObservedUtil.unRegisteredObserve(l);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLifeCycleObservedUtil.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        mLifeCycleObservedUtil.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLifeCycleObservedUtil.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLifeCycleObservedUtil.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLifeCycleObservedUtil.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLifeCycleObservedUtil.onStop();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mLifeCycleObservedUtil.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mLifeCycleObservedUtil.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLifeCycleObservedUtil.onActivityResult(requestCode, resultCode, data);
    }
}
