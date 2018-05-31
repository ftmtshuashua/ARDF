package com.lfp.androidrapiddevelopmentframework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.ardf.debug.LogUtil;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;

import java.text.MessageFormat;

/**
 * 开屏页面<br/>
 * Created by LiFuPing on 2018/5/31.
 */
public class LoadingActivity extends BaseActivity {
    SplashAD mSplashAD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        mSplashAD = new SplashAD(getActivity(), (ViewGroup) findViewById(R.id.view_Splash), Constants.APPID, Constants.SplashPosID, mSplashADListener, 3000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    SplashADListener mSplashADListener = new SplashADListener() {
        @Override
        public void onADPresent() {
            LogUtil.e("LoadingActivity ---->> onADPresent");
        }

        @Override
        public void onADDismissed() {
            LogUtil.e("LoadingActivity ---->> onADDismissed");
            next();
        }

        @Override
        public void onNoAD(AdError adError) {
            LogUtil.e(MessageFormat.format("LoadingActivity ---->> onNoAD:{0} - {1}", adError.getErrorCode(), adError.getErrorMsg()));
            next();
        }

        @Override
        public void onADClicked() {
            LogUtil.e("LoadingActivity ---->> onADClicked");
            next();
        }

        @Override
        public void onADTick(long l) {
            LogUtil.e(MessageFormat.format("LoadingActivity ---->> onADTick:{0,number}", l));
        }
    };

    void next() {
        MainActivity.start(getActivity());
        finish();
    }
}
