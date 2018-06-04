package com.lfp.ardf;

import android.content.Context;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.util.ApkUtil;
import com.lfp.ardf.util.AssetsUtil;
import com.lfp.ardf.util.PhoneUtil;
import com.lfp.ardf.util.ScreenUtil;
import com.lfp.ardf.util.ToastUtil;
import com.lfp.ardf.util.Utils;

/**
 * 帮助框架做一些必要的初始化工作
 * Created by LiFuPing on 2018/5/9.
 */
public class AppFrameworkHolper {
    /**
     * 框架初始化
     */
    public static final void init(Context c) {
        Utils.checkNotNull(c);
        Context context = c.getApplicationContext();
        LogUtil.init(context);
        LogUtil.i("----------- AppFramework init -----------");
        ToastUtil.init(context);
        ScreenUtil.init(context);
        ApkUtil.init(context);
        PhoneUtil.init(context);
        AssetsUtil.init(context);
    }

}
