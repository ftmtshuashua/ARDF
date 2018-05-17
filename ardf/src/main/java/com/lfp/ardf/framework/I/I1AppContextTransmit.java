package com.lfp.ardf.framework.I;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

/**
 * 特殊上下文方法转发的(主要对Activity和Fragment中某些方法使用方式不同的情况做了处理)
 * Created by LiFuPing on 2018/5/11.
 */
public interface I1AppContextTransmit {

    void startActivity(Intent intent);

    void startActivity(Intent intent, @Nullable Bundle options);

    void startActivityForResult(Intent intent, int requestCode);

    void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options);

    /**
     * 在Activity中等同与getFragmentManager()
     * <br/>
     * 在Fragment中等同与getChildFragmentManager()
     * * @return FragmentManager
     */
    FragmentManager getSmartFragmentManager();

    /**
     * 获得上下文
     */
    Context getContext();

    /**
     * 获得Activity
     */
    Activity getActivity();

}
