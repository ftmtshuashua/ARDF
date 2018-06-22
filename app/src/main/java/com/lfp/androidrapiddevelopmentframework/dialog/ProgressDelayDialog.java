package com.lfp.androidrapiddevelopmentframework.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.ardf.dialog.BaseDelayDialog;
import com.lfp.ardf.framework.I.IAppFramework;

/**
 * 自定义延时等待Dialog<br/>
 * Created by LiFuPing on 2018/5/31.
 */
public class ProgressDelayDialog extends BaseDelayDialog {

    public ProgressDelayDialog(@NonNull Context c) {
        super(c);
    }

    public ProgressDelayDialog(@NonNull IAppFramework appfk) {
        super(appfk);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        setCanceledOnTouchOutside(false);
        setDelayTime(DEFUALT_DELAY_TIME);
    }
}
