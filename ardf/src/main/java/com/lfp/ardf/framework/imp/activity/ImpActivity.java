package com.lfp.ardf.framework.imp.activity;

import android.app.Activity;
import android.content.Context;

import com.lfp.ardf.framework.I.IAppFramework;

/**
 * 框架接入
 * Created by LiFuPing on 2018/5/10.
 */
public class ImpActivity extends Imp3NetRequestActivity implements IAppFramework {

    public Activity getActivity() {
        return this;
    }

    public Context getContext() {
        return this;
    }

    @Override
    public IAppFramework getAppFk() {
        return this;
    }

}
