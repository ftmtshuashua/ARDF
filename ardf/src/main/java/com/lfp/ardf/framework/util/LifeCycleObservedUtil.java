package com.lfp.ardf.framework.util;

import android.content.Intent;
import android.os.Bundle;

import com.lfp.ardf.framework.I.ILifeCycleObserve;
import com.lfp.ardf.framework.I.ILifeCycleObserved;

import java.util.LinkedList;

/**
 * 生命周期观察工具
 * Created by LiFuPing on 2017/9/8.
 */
public class LifeCycleObservedUtil implements ILifeCycleObserved, ILifeCycleObserve {
    LinkedList<ILifeCycleObserve> mObserves = new LinkedList<>();

    @Override
    public void registeredObserve(ILifeCycleObserve l) {
        synchronized (this) {
            mObserves.add(l);
        }
    }

    @Override
    public void unRegisteredObserve(ILifeCycleObserve l) {
        synchronized (this) {
            mObserves.remove(l);
        }
    }

    @Override
    public void onDestroy() {
        if (mObserves.isEmpty()) return;
        synchronized (this) {
            for (int i = mObserves.size() - 1; i >= 0; i--) {
                ILifeCycleObserve obs = mObserves.get(i);
                obs.onDestroy();
            }
            mObserves.clear(); //移除所有监听
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mObserves.isEmpty()) return;
        synchronized (this) {
            for (int i = mObserves.size() - 1; i >= 0; i--) {
                ILifeCycleObserve obs = mObserves.get(i);
                obs.onSaveInstanceState(outState);
            }
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (mObserves.isEmpty()) return;
        synchronized (this) {
            for (int i = mObserves.size() - 1; i >= 0; i--) {
                ILifeCycleObserve obs = mObserves.get(i);
                obs.onRestoreInstanceState(savedInstanceState);
            }
        }
    }

    @Override
    public void onStart() {
        if (mObserves.isEmpty()) return;
        synchronized (this) {
            for (int i = mObserves.size() - 1; i >= 0; i--) {
                ILifeCycleObserve obs = mObserves.get(i);
                obs.onStart();
            }
        }
    }

    @Override
    public void onResume() {
        if (mObserves.isEmpty()) return;
        synchronized (this) {
            for (int i = mObserves.size() - 1; i >= 0; i--) {
                ILifeCycleObserve obs = mObserves.get(i);
                obs.onResume();
            }
        }
    }

    @Override
    public void onPause() {
        if (mObserves.isEmpty()) return;
        synchronized (this) {
            for (int i = mObserves.size() - 1; i >= 0; i--) {
                ILifeCycleObserve obs = mObserves.get(i);
                obs.onPause();
            }
        }
    }

    @Override
    public void onStop() {
        if (mObserves.isEmpty()) return;
        synchronized (this) {
            for (int i = mObserves.size() - 1; i >= 0; i--) {
                ILifeCycleObserve obs = mObserves.get(i);
                obs.onStop();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mObserves.isEmpty()) return;
        synchronized (this) {
            for (int i = mObserves.size() - 1; i >= 0; i--) {
                ILifeCycleObserve obs = mObserves.get(i);
                obs.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

}
