package com.lfp.androidrapiddevelopmentframework.app;

import android.app.Application;
import android.content.Context;

import com.lfp.ardf.AppFrameworkHolper;
import com.lfp.ardf.config.FileCacheConfig;
import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.util.CpuUtile;
import com.lfp.ardf.util.PhoneUtil;
import com.lfp.ardf.util.ScreenUtil;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import java.text.MessageFormat;

/**
 * Created by LiFuPing on 2018/5/9.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppFrameworkHolper.init(getApplicationContext());
        FileCacheConfig.init(getApplicationContext(), "ARDF");
        LogUtil.i_Pretty(MessageFormat.format("{0}\n\n{1}\n\n{2}", PhoneUtil.getPhoneInfo(), ScreenUtil.getScreenInfo(), CpuUtile.getCupInfo()));


        xgPush(this);
    }


    /*推送*/
    void xgPush(Context c) {
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
}
