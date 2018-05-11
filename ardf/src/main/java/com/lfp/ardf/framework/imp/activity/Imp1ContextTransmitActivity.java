package com.lfp.ardf.framework.imp.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.lfp.ardf.framework.I.I1AppContextTransmit;


/**
 * Created by LiFuPing on 2018/5/11.
 */
public class Imp1ContextTransmitActivity extends IActivity implements I1AppContextTransmit {

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

}
