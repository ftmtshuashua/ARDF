package com.lfp.androidrapiddevelopmentframework.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.event.DemoEvent;
import com.lfp.androidrapiddevelopmentframework.util.ActionBarControl;
import com.lfp.androidrapiddevelopmentframework.widget.WebProgressBar;
import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.module.net.util.UrlFormat;
import com.lfp.ardf.util.Utils;
import com.lfp.ardf.util.ViewUtil;
import com.lfp.ardf.widget.WebViewFk;

import java.io.Serializable;

/**
 * Web浏览器<br>
 * Created by LiFuPing on 2018/6/15.
 */
public class Demo_WebActivity extends BaseActivity {
    private static final String KEY_DATA = "KEY_DATA";

    public static final void start(IAppFramework appfk, String url) {
        start(appfk, new Config(url));
    }

    public static final void start(IAppFramework appfk, Config config) {
        if (config == null) throw new NullPointerException("Config不能为空!");
        if (TextUtils.isEmpty(config.getUrl())) throw new NullPointerException("Url不能为空!");

        Intent intent = new Intent(appfk.getContext(), Demo_WebActivity.class);
        intent.putExtra(KEY_DATA, config);
        appfk.startActivity(intent);
    }


    WebProgressBar mWebProgressBar;
    WebViewFk mWebViewFx;
    ActionBarControl mActionBarControl;
    Config mConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfig = (Config) getIntent().getSerializableExtra(KEY_DATA);
        setContentView(R.layout.activity_web);
        initActionBar(mConfig);

        mWebProgressBar = findViewById(R.id.view_WebProgressBar);
        mWebProgressBar.setMaxProgress(100);

        mWebViewFx = findViewById(R.id.view_WebView);
        mWebViewFx.loadUrl(mConfig.getUrl());
        mWebViewFx.setOnLoadingProgressChangeLisenter(mOnLoadingProgressChangeLisenter);
        mWebViewFx.setOnTitleChange(mOnTitleChange);
    }


    WebViewFk.OnLoadingProgressChangeLisenter mOnLoadingProgressChangeLisenter = new WebViewFk.OnLoadingProgressChangeLisenter() {

        @Override
        public void onWebStart(WebView view, String url, Bitmap favicon) {
            ViewUtil.setVisibility(mWebProgressBar, View.VISIBLE);
        }

        @Override
        public void onWebLoading(boolean finish, int progress) {
            mWebProgressBar.setProgress(progress);
        }

        @Override
        public void onWebFinish(WebView view, String url) {
            ViewUtil.setVisibility(mWebProgressBar, View.GONE);
            if (view.canGoBack()) mActionBarControl.showFinish();
        }
    };
    WebViewFk.OnTitleChange mOnTitleChange = new WebViewFk.OnTitleChange() {
        @Override
        public void onTitleChange(String title) {
            mActionBarControl.setTitle(title);
        }
    };

    void initActionBar(Config config) {
        mActionBarControl = new ActionBarControl(getActivity());
        mActionBarControl.setTitle(!Utils.isEmpty(config.getTitle()) ? config.getTitle() : config.getUrl());
        mActionBarControl.showBack();
        mActionBarControl.setBackOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebViewFx!=null && mWebViewFx.canGoBack()) {
                    mWebViewFx.goBack();
                } else {
                    finish();
                }
            }
        });
        mActionBarControl.setFinishOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static final class Config implements Serializable {
        /*禁用自动标题,禁用之后标题不会随着页面跳转而变化*/
        static final int FLAG_DISABLED_AUTO_TITLE = 0x1;
        /*禁用URL修复,*/
        static final int FLAG_DISABLED_URL_FIX = 0x1 << 1;

        String url;
        String title;
        int flag;

        public Config(String url) {
            this.url = url;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            if ((flag & FLAG_DISABLED_URL_FIX) != 0) return url;
            return UrlFormat.fixUrl(url);
        }

        public String getTitle() {
            return title;
        }

        /**
         * 禁用自动标题,禁用之后标题不会随着页面跳转而变化
         */
        public void disableAutoTitle() {
            flag |= FLAG_DISABLED_AUTO_TITLE;
        }
    }

    public static final class Demo extends DemoEvent {

        public Demo(IAppFramework appfk) {
            super(appfk, "WebViewFk", "Web浏览器");
        }

        @Override
        public void call() {
            Demo_WebActivity.start(getAppFk(), "baidu");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
