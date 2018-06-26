package com.lfp.ardf.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * 提示消息
 */
public class ToastUtils {
    private static ToastUtils mInstance;
    private Toast toast;

    public static ToastUtils init(Context c) {
        if (mInstance == null) {
            synchronized (ToastUtils.class) {
                if (mInstance == null) mInstance = new ToastUtils(c.getApplicationContext());
            }
        }
        return mInstance;
    }


    private ToastUtils(Context c) {
        toast = Toast.makeText(c, "", Toast.LENGTH_LONG);
    }

    public static final void show(String text) {
        if (mInstance != null && Utils.isMainThread() && !Utils.isEmpty(text)) {
            mInstance.toast.setText(text);
            mInstance.toast.show();
        }
    }

    public static final void show(@StringRes int resid) {
        if (mInstance != null && Utils.isMainThread()) {
            mInstance.toast.setText(resid);
            mInstance.toast.show();
        }
    }

}
