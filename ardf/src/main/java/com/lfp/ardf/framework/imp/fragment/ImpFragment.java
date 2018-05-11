package com.lfp.ardf.framework.imp.fragment;

import android.support.v4.app.FragmentManager;

import com.lfp.ardf.framework.I.IAppFramework;

/**
 * Created by LiFuPing on 2018/5/11.
 */
public class ImpFragment extends Imp2LifeCycleObservedFragment implements IAppFramework {
    @Override
    public IAppFramework getAppFk() {
        return this;
    }
}
