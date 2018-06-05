package com.lfp.androidrapiddevelopmentframework.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lfp.androidrapiddevelopmentframework.api.Apiserver;
import com.lfp.ardf.framework.imp.fragment.ImpFragment;

/**
 * Created by LiFuPing on 2018/5/17.
 */
public class BaseFragment extends ImpFragment {


    /*获得API配置*/
    protected Apiserver getApiserver() {
        return Apiserver.getInstace();
    }

}
