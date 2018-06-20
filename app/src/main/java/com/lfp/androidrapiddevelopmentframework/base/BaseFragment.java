package com.lfp.androidrapiddevelopmentframework.base;

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
