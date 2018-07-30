package com.lfp.jpush;

import com.lfp.ardf.util.AppUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * <pre>
 * desc:
 *      极光推送
 * function:
 *
 * Created by LiFuPing on 2018/7/30.
 * </pre>
 */
public class JPushHolper {

    public static void init() {
        JPushInterface.setDebugMode(true); /*调试模式*/
        JPushInterface.init(AppUtils.getApp());
    }


}
