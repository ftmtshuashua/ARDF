package com.lfp.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.util.BadgeUtils;

import java.text.MessageFormat;

import cn.jpush.android.api.JPushInterface;

/**
 * <pre>
 * desc:
 *
 * function:
 *
 * Created by LiFuPing on 2018/7/30.
 * </pre>
 */
public class JPushReceiver_Custom extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.e(MessageFormat.format("收到来着JPush的推送消息:{0}", intent));

        Bundle bundle = intent.getExtras();
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtil.e(MessageFormat.format("Message:{0}", message));
            LogUtil.e(MessageFormat.format("Extras:{0}", extras));
            LogUtil.e(MessageFormat.format("alert:{0}", alert));
            try {
                int count = Integer.parseInt(message);
                BadgeUtils.setBadge(count);
            } catch (Exception e) {
            }

        }
    }
}
