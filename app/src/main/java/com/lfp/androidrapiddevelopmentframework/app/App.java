package com.lfp.androidrapiddevelopmentframework.app;

import android.app.Application;
import android.content.Context;

import com.lfp.androidrapiddevelopmentframework.Constants;
import com.lfp.ardf.AppFrameworkHolper;
import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.util.CpuUtils;
import com.lfp.ardf.util.cache.FileCacheConfig;
import com.lfp.ardf.util.PhoneUtils;
import com.lfp.ardf.util.ScreenUtils;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;

import java.text.MessageFormat;

import lib.gg.y.Y_AdHolper;

/**
 * Created by LiFuPing on 2018/5/9.
 */
public class App extends Application {

    static {
        System.loadLibrary("base");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppFrameworkHolper.init(getApplicationContext());
        FileCacheConfig.init(getApplicationContext(), "ARDF");
        LogUtil.i_Pretty(MessageFormat.format("{0}\n\n{1}\n\n{2}", PhoneUtils.getPhoneInfo(), ScreenUtils.getScreenInfo(), CpuUtils.getCupInfo()));
        LogUtil.e(MessageFormat.format("动态注册数据获取:{0}", Constants.JniLoadding("获取NDK数据 ")));

        ThirdParty.init(this);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        ThirdParty.onTerminate(this);
    }


    /*三方功能*/
    static class ThirdParty {
        static void init(Context c) {
            LogUtil.e("app started,init third party procedure...");
            xgPush(c);
            y_m_a_d(c);
            statistics(c);
        }

        static void onTerminate(Context c) {
            LogUtil.e("app exit, close third party procedure...");
            Y_AdHolper.onExit(c);
        }

        /*推送*/
        static void xgPush(Context c) {
            LogUtil.e("open push...");
            Context context = c.getApplicationContext();
            XGPushConfig.enableDebug(context, LogUtil.isDebug());
            XGPushConfig.setReportNotificationStatusEnable(context, LogUtil.isDebug());
            XGPushConfig.setHuaweiDebug(true);

            XGPushManager.registerPush(context);
//, new XGIOperateCallback() {
//            @Override
//            public void onSuccess(Object data, int flag) {//token在设备卸载重装的时候有可能会变
//                LogUtil.e(MessageFormat.format("TPush 注册成功，设备token为：{0}", data));
//            }
//
//            @Override
//            public void onFail(Object data, int errCode, String msg) {
//                LogUtil.e(MessageFormat.format("TPush 注册失败，错误码：{0},错误信息：{1}", errCode, msg));
//            }
//        }

//        XGPushManager.bindAccount(context, "XINGE"); //设置账号
//        XGPushManager.setTag(context,"XINGE");//设置标签
        }

        static void y_m_a_d(Context c) {
            LogUtil.e("open y_m_a_d...");
            Y_AdHolper.init(c, LogUtil.isDebug());
        }

        /*统计*/
        static void statistics(Context c) {
            LogUtil.e("open statistics...");
            try {
                StatConfig.setDebugEnable(false);
                StatService.startStatService(c, null, com.tencent.stat.common.StatConstants.VERSION);
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }
    }

}
