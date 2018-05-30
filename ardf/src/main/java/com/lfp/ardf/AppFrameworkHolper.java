package com.lfp.ardf;

import android.content.Context;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.util.ScreenUtil;
import com.lfp.ardf.util.ToastUtil;
import com.lfp.ardf.util.Utils;

import java.text.MessageFormat;

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
        LogUtil.init(c.getApplicationContext());
        LogUtil.i("----------- AppFramework init -----------");
        ToastUtil.init(c.getApplicationContext());
        ScreenUtil.init(c.getApplicationContext());

        /*配置缓存目录*/
        LogUtil.i(MessageFormat.format("配置缓存目录:{0}", ""));

    }

}
