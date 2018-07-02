package com.lfp.ardf.framework.I;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

/**
 * <pre>
 * desc:
 *      特殊上下文方法转发的(主要对Activity和Fragment中某些方法使用方式不同的情况做了处理)
 * function:
 *
 * Created by LiFuPing on 2018/5/11.
 * </pre>
 */
public interface I1AppContextTransmit {

    /**
     * startActivity
     *
     * @param intent Intent
     */
    void startActivity(Intent intent);

    /**
     * startActivity
     *
     * @param intent  Intent
     * @param options Bundle
     */
    void startActivity(Intent intent, @Nullable Bundle options);

    /**
     * startActivityForResult
     *
     * @param intent      Intent
     * @param requestCode int
     */
    void startActivityForResult(Intent intent, int requestCode);

    /**
     * startActivityForResult
     *
     * @param intent      Intent
     * @param requestCode int
     * @param options     Bundle
     */
    void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options);

    /**
     * 在Activity中等同与getSupportFragmentManager()
     * <br>
     * 在Fragment中等同与getChildFragmentManager()
     *
     * @return FragmentManager
     */
    FragmentManager getSmartFragmentManager();

    /**
     * @return 获得Context
     */
    Context getContext();

    /**
     * @return 获得Activity
     */
    Activity getActivity();

}
