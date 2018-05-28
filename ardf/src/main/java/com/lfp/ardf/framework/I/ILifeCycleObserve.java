package com.lfp.ardf.framework.I;

import android.content.Intent;
import android.os.Bundle;

/**
 * 生命周期订阅 - 利用观测者模式使业务与Activity或者Fragment接耦合<br/>
 * <p>
 * Created by LiFuPing on 2017/9/4.
 */
public interface ILifeCycleObserve {

    /**
     * 回调方法
     * {@link com.lfp.ardf.framework.imp.activity.Imp2LifeCycleObservedActivity  Activity.onCreate(Bundle savedInstanceState)}
     * 与
     * {@link com.lfp.ardf.framework.imp.fragment.Imp2LifeCycleObservedFragment Fragment.onViewCreated(View view, Bundle savedInstanceState)}
     *
     * @param savedInstanceState savedInstanceState
     */
    void onCreate(Bundle savedInstanceState);

    /**
     * 回调方法
     * {@link com.lfp.ardf.framework.imp.activity.Imp2LifeCycleObservedActivity  Activity.onDestroy()}
     * 与
     * {@link com.lfp.ardf.framework.imp.fragment.Imp2LifeCycleObservedFragment Fragment.onDestroy()}
     * <br/>
     * 回调在 super.onDestroy() 之前
     */
    void onDestroy();

    /**
     * 回调方法
     * {@link com.lfp.ardf.framework.imp.activity.Imp2LifeCycleObservedActivity  Activity.onSaveInstanceState(Bundle outState)}
     * 与
     * {@link com.lfp.ardf.framework.imp.fragment.Imp2LifeCycleObservedFragment Fragment.onSaveInstanceState(Bundle outState)}
     */
    void onSaveInstanceState(Bundle outState);

    /**
     * 回调方法
     * {@link com.lfp.ardf.framework.imp.activity.Imp2LifeCycleObservedActivity  Activity.onRestoreInstanceState(Bundle savedInstanceState)}
     * 与
     * {@link com.lfp.ardf.framework.imp.fragment.Imp2LifeCycleObservedFragment Fragment.onRestoreInstanceState(Bundle savedInstanceState)}
     */
    void onRestoreInstanceState(Bundle savedInstanceState);

    /**
     * 回调方法
     * {@link com.lfp.ardf.framework.imp.activity.Imp2LifeCycleObservedActivity  onStart()}
     * 与
     * {@link com.lfp.ardf.framework.imp.fragment.Imp2LifeCycleObservedFragment onStart()}
     */
    void onStart();

    /**
     * 回调方法
     * {@link com.lfp.ardf.framework.imp.activity.Imp2LifeCycleObservedActivity  Activity.onResume()}
     * 与
     * {@link com.lfp.ardf.framework.imp.fragment.Imp2LifeCycleObservedFragment Fragment.onResume()}
     */
    void onResume();

    /**
     * 回调方法
     * {@link com.lfp.ardf.framework.imp.activity.Imp2LifeCycleObservedActivity  Activity.onPause()}
     * 与
     * {@link com.lfp.ardf.framework.imp.fragment.Imp2LifeCycleObservedFragment Fragment.onPause()}
     */
    void onPause();

    /**
     * 回调方法
     * {@link com.lfp.ardf.framework.imp.activity.Imp2LifeCycleObservedActivity  Activity.onStop()}
     * 与
     * {@link com.lfp.ardf.framework.imp.fragment.Imp2LifeCycleObservedFragment Fragment.onStop()}
     */
    void onStop();

    /**
     * 回调方法
     * {@link com.lfp.ardf.framework.imp.activity.Imp2LifeCycleObservedActivity  Activity.onActivityResult(int requestCode, int resultCode, Intent data)}
     * 与
     * {@link com.lfp.ardf.framework.imp.fragment.Imp2LifeCycleObservedFragment Fragment.onActivityResult(int requestCode, int resultCode, Intent data)}
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
