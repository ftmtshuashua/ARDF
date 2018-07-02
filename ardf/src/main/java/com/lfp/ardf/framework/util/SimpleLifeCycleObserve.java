package com.lfp.ardf.framework.util;

import android.content.Intent;
import android.os.Bundle;

import com.lfp.ardf.framework.I.ILifeCycleObserve;


/**
 * <pre>
 * desc:
 *      ILifeCycleObserve简单实现,通常只需要创建本类并重载需要的函数即可
 * function:
 *
 * Created by LiFuPing on 2017/9/4.
 * </pre>
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
