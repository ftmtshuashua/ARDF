package com.lfp.ardf;

import android.content.Context;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.util.ToastUtil;

/**
 * 帮助框架做一些必要的初始化工作
 * Created by LiFuPing on 2018/5/9.
 */
public class AppFrameworkHolper {
    /**
     * 初始化
     */
    public static final void init(Context c) {
        LogUtil.init(c.getApplicationContext());
        LogUtil.i("----------- AppFramework init() -----------");
        ToastUtil.init(c.getApplicationContext());


    }

}
