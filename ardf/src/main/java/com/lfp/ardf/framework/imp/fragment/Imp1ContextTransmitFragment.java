package com.lfp.ardf.framework.imp.fragment;

import android.support.v4.app.FragmentManager;

import com.lfp.ardf.framework.I.I1AppContextTransmit;

/**
 * Created by LiFuPing on 2018/5/11.
 */
public class Imp1ContextTransmitFragment extends IFragment implements I1AppContextTransmit {
    @Override
    public FragmentManager getSmartFragmentManager() {
        return getChildFragmentManager();
    }
}
