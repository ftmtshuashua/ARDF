package com.lfp.ardf.framework.util;

import android.content.Intent;
import android.os.Bundle;

import com.lfp.ardf.framework.I.ILifeCycleObserve;


/**
 * 将注意力集中到需要使用的方法中<br>
 * Created by LiFuPing on 2017/9/4.
 */
public abstract class SimpleLifeCycleObserve implements ILifeCycleObserve {

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
