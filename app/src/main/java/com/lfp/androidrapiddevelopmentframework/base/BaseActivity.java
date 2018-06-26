package com.lfp.androidrapiddevelopmentframework.base;

import android.view.KeyEvent;

import com.lfp.androidrapiddevelopmentframework.api.Apiserver;
import com.lfp.ardf.framework.imp.activity.ImpActivity;
import com.tencent.stat.StatService;

/**
 * Created by LiFuPing on 2018/5/11.
 */
public class BaseActivity extends ImpActivity {

    /*拦截back键*/
    protected static final int FLAG_INTERCEPT_KEYCODE_BACK = 0x1;
    /*拦截home键*/
    protected static final int FLAG_INTERCEPT_KEYCODE_HOME = 0x1 << 1;

    int flag;

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    /*获得API配置*/
    protected Apiserver getApiserver() {
        return Apiserver.getInstace();
    }

//    public void set

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && ((flag & FLAG_INTERCEPT_KEYCODE_BACK) != 0))
            return true;
        if (keyCode == KeyEvent.KEYCODE_HOME && ((flag & FLAG_INTERCEPT_KEYCODE_HOME) != 0))
            return true;
        return super.onKeyDown(keyCode, event);
    }

}
