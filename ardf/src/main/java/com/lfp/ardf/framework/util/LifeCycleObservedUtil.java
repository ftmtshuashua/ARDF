package com.lfp.ardf.framework.util;

import android.content.Intent;
import android.os.Bundle;

import com.lfp.ardf.framework.I.ILifeCycleObserve;
import com.lfp.ardf.framework.I.ILifeCycleObserved;

import java.util.Vector;

/**
 * 生命周期观察工具
 * Created by LiFuPing on 2017/9/8.
 */
public class LifeCycleObservedUtil implements ILifeCycleObserved, ILifeCycleObserve {
    private Vector<ILifeCycleObserve> obs;

    public LifeCycleObservedUtil() {
        obs = new Vector<>();
    }

    @Override
    public synchronized void registeredObserve(ILifeCycleObserve o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.addElement(o);
        }
    }

    @Override
    public synchronized void unRegisteredObserve(ILifeCycleObserve o) {
        obs.removeElement(o);
    }


    /**
     * 返回这个可观察到的物体的观察者的数量。
     *
     * @return 这个物体的观察者的数量。
     */
    public synchronized int countObservers() {
        return obs.size();
    }


    synchronized ILifeCycleObserve[] loop() {
        return obs.toArray(new ILifeCycleObserve[obs.size()]);
    }

    @Override
    public synchronized void onDestroy() {
        ILifeCycleObserve[] arrLocal = loop();
        for (int i = arrLocal.length - 1; i >= 0; i--)
            arrLocal[i].onDestroy();
        obs.removeAllElements();
    }

    @Override
    public synchronized void onSaveInstanceState(Bundle outState) {
        ILifeCycleObserve[] arrLocal = loop();
        for (int i = arrLocal.length - 1; i >= 0; i--)
            arrLocal[i].onSaveInstanceState(outState);
    }

    @Override
    public synchronized void onRestoreInstanceState(Bundle savedInstanceState) {
        ILifeCycleObserve[] arrLocal = loop();
        for (int i = arrLocal.length - 1; i >= 0; i--)
            arrLocal[i].onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public synchronized void onStart() {
        ILifeCycleObserve[] arrLocal = loop();
        for (int i = arrLocal.length - 1; i >= 0; i--)
            arrLocal[i].onStart();
    }

    @Override
    public synchronized void onResume() {
        ILifeCycleObserve[] arrLocal = loop();
        for (int i = arrLocal.length - 1; i >= 0; i--)
            arrLocal[i].onResume();
    }

    @Override
    public synchronized void onPause() {
        ILifeCycleObserve[] arrLocal = loop();
        for (int i = arrLocal.length - 1; i >= 0; i--)
            arrLocal[i].onPause();
    }

    @Override
    public synchronized void onStop() {
        ILifeCycleObserve[] arrLocal = loop();
        for (int i = arrLocal.length - 1; i >= 0; i--)
            arrLocal[i].onStop();
    }

    @Override
    public synchronized void onActivityResult(int requestCode, int resultCode, Intent data) {
        ILifeCycleObserve[] arrLocal = loop();
        for (int i = arrLocal.length - 1; i >= 0; i--)
            arrLocal[i].onActivityResult(requestCode, resultCode, data);
    }

}
