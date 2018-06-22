package com.lfp.androidrapiddevelopmentframework.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.ardf.dialog.BaseDialog;
import com.lfp.ardf.framework.I.IAppFramework;

/**
 * 自定义等待Dialog <br/>
 * Created by LiFuPing on 2018/5/31.
 */
public class ProgressDialog extends BaseDialog {
    public ProgressDialog(@NonNull Context c) {
        super(c);
    }

    public ProgressDialog(@NonNull IAppFramework appfk) {
        super(appfk);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        setCanceledOnTouchOutside(false);
    }
}
