package com.lfp.ardf;

import android.content.Context;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.util.AppUtils;
import com.lfp.ardf.util.Utils;

/**
 * 帮助框架做一些必要的初始化工作
 * Created by LiFuPing on 2018/5/9.
 */
public class AppFrameworkHolper {

    static {
        System.loadLibrary("calculate");
    }

    /**
     * 框架初始化
     *
     * @param c context
     */
    public static final void init(Context c) {
//        Context context = c.getApplicationContext();
//        Utils.checkNotNull(c);

        Context context  = AppUtils.getApp();
        LogUtil.init(context);
        LogUtil.i("----------- AppFramework init -----------");
    }

}
