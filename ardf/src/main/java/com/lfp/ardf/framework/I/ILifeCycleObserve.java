package com.lfp.ardf.framework.I;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * <pre>
 * desc:
 *      生命周期订阅 - 利用观测者模式使业务与Activity和Fragment解耦
 *      使用{@link com.lfp.ardf.framework.util.SimpleLifeCycleObserve}
 * function:
 *
 * Created by LiFuPing on 2017/9/4.
 * </pre>
 */
public interface ILifeCycleObserve {

    /**
     * 回调方法
     * {@link  Activity#onCreate}
     * 与
     * {@link Fragment#onViewCreated}
     *
     * @param savedInstanceState savedInstanceState Bundle
     */
    void onCreate(Bundle savedInstanceState);

    /**
     * 回调方法
     * {@link Activity#onDestroy}
     * 与
     * {@link Fragment#onDestroy}
     * <br>
     * 回调在 super.onDestroy() 之前
     */
    void onDestroy();

    /**
     * 回调方法
     * {@link Activity#onSaveInstanceState(Bundle outState)}
     * 与
     * {@link Fragment#onSaveInstanceState(Bundle outState)}
     *
     * @param outState Bundle
     */
    void onSaveInstanceState(Bundle outState);

    /**
     * 回调方法
     * {@link Activity#onRestoreInstanceState}
     * 与
     * {@link Fragment#onActivityCreated}
     *
     * @param savedInstanceState Bundle
     */
    void onRestoreInstanceState(Bundle savedInstanceState);

    /**
     * 回调方法
     * {@link Activity#onStart}
     * 与
     * {@link Fragment#onStart}
     */
    void onStart();

    /**
     * 回调方法
     * {@link Activity#onResume}
     * 与
     * {@link Fragment#onResume}
     */
    void onResume();

    /**
     * 回调方法
     * {@link Activity#onPause}
     * 与
     * {@link Fragment#onPause}
     */
    void onPause();

    /**
     * 回调方法
     * {@link Activity#onStop}
     * 与
     * {@link Fragment#onStop}
     */
    void onStop();

    /**
     * 回调方法
     * {@link Activity#onActivityResult}
     * 与
     * {@link Fragment#onActivityResult}
     *
     * @param requestCode int
     * @param resultCode  int
     * @param data        Intent
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
