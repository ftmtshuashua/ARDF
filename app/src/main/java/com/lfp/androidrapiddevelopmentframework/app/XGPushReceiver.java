package com.lfp.androidrapiddevelopmentframework.app;

import android.content.Context;

import com.lfp.ardf.debug.LogUtil;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import java.text.MessageFormat;

/**
 * <br>
 * Created by LiFuPing on 2018/6/21.
 */
public class XGPushReceiver extends XGPushBaseReceiver {
    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {//注册回调
        LogUtil.e(MessageFormat.format("TPush onRegisterResult:{0} _ [{1}]", i,xgPushRegisterResult.toString()));
    }

    @Override
    public void onUnregisterResult(Context context, int i) {//	反注册回调
        LogUtil.e(MessageFormat.format("TPush onUnregisterResult:{0}", i));
    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {//设置标签回调
        LogUtil.e(MessageFormat.format("TPush onSetTagResult:{0} _ [{1}]",i, s));

    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {//删除标签回调
        LogUtil.e(MessageFormat.format("TPush onDeleteTagResult:{0} _ [{1}]",i, s));
    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {//应用内消息的回调
        LogUtil.e(MessageFormat.format("TPush onDeleteTagResult:[{0}]",xgPushTextMessage.toString()));

    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {//通知被点击触发的回调
        LogUtil.e(MessageFormat.format("TPush onNotifactionClickedResult:[{0}]",xgPushClickedResult.toString()));
    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {//通知被展示触发的回调，可以在此保存APP收到的通知
        LogUtil.e(MessageFormat.format("TPush onNotifactionShowedResult:[{0}]",xgPushShowedResult.toString()));
    }
}
