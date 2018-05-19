package com.lfp.ardf.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;


public class ToastUtil {
    private static ToastUtil mInstance;
    private Toast toast;

    public static ToastUtil init(Context c) {
        if (mInstance == null) {
            synchronized (ToastUtil.class) {
                if (mInstance == null) mInstance = new ToastUtil(c.getApplicationContext());
            }
        }
        return mInstance;
    }


    private ToastUtil(Context c) {
        toast = Toast.makeText(c, "", Toast.LENGTH_LONG);
    }

    public static final void show(String text) {
        if (mInstance == null) return;
        if (TextUtils.isEmpty(text)) return;
        mInstance.toast.setText(text);
        mInstance.toast.show();
    }

    public static final void show(int resid) {
        if (mInstance == null) return;
        mInstance.toast.setText(resid);
        mInstance.toast.show();
    }
}
