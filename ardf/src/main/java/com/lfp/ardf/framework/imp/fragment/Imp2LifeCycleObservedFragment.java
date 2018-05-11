package com.lfp.ardf.framework.imp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lfp.ardf.framework.I.ILifeCycleObserve;
import com.lfp.ardf.framework.I.ILifeCycleObserved;
import com.lfp.ardf.framework.util.LifeCycleObservedUtil;

/**
 * Created by LiFuPing on 2018/5/11.
 */
public class Imp2LifeCycleObservedFragment extends Imp1ContextTransmitFragment implements ILifeCycleObserved {
    LifeCycleObservedUtil mLifeCycleObservedUtil = new LifeCycleObservedUtil();

    @Override
    public void registeredObserve(ILifeCycleObserve l) {
        mLifeCycleObservedUtil.registeredObserve(l);
    }

    @Override
    public void unRegisteredObserve(ILifeCycleObserve l) {
        mLifeCycleObservedUtil.unRegisteredObserve(l);
    }

    @Override
    public void onDestroy() {
        mLifeCycleObservedUtil.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        mLifeCycleObservedUtil.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mLifeCycleObservedUtil.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mLifeCycleObservedUtil.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mLifeCycleObservedUtil.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mLifeCycleObservedUtil.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLifeCycleObservedUtil.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLifeCycleObservedUtil.onActivityResult(requestCode, resultCode, data);
    }

}
