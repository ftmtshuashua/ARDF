package com.lfp.ardf;

import android.content.Context;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.util.ApkUtils;
import com.lfp.ardf.util.AssetsUtils;
import com.lfp.ardf.util.FileUtils;
import com.lfp.ardf.util.PhoneUtils;
import com.lfp.ardf.util.ScreenUtils;
import com.lfp.ardf.util.ToastUtils;
import com.lfp.ardf.util.Utils;

/**
 * 帮助框架做一些必要的初始化工作
 * Created by LiFuPing on 2018/5/9.
 */
public class AppFrameworkHolper {
    /**
     * 框架初始化
     *
     * @param c context
     */
    public static final void init(Context c) {
        Utils.checkNotNull(c);
        Context context = c.getApplicationContext();
        LogUtil.init(context);
        LogUtil.i("----------- AppFramework init -----------");
        ToastUtils.init(context);
        ScreenUtils.init(context);
        ApkUtils.init(context);
        PhoneUtils.init(context);
        AssetsUtils.init(context);
        FileUtils.init(context);
    }

}
