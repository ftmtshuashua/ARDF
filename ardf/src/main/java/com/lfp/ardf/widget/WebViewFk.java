package com.lfp.ardf.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lfp.ardf.util.cache.FileCacheConfig;
import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.framework.I.ILifeCycleObserve;
import com.lfp.ardf.framework.util.SimpleLifeCycleObserve;
import com.lfp.ardf.util.AppUtils;

import java.io.File;
import java.text.MessageFormat;

/**
 * 浏览器框架
 * Created by LiFuPing on 2018/4/11.
 */
public class WebViewFk extends WebView {

    /**
     * 一个空白页面地址
     */
    public static final String URL_BLANK = "about:blank";

    OnLoadingProgressChangeLisenter mOnLoadingProgressChangeLisenter;
    /**
     * 监听Title变化
     */
    OnTitleChange mOnTitleChange;

    /**
     * 清除Cookies
     */
    public void clearCookies() {
        CookieManager.getInstance().removeAllCookie();
        CookieSyncManager.createInstance(getContext()).sync();
    }

    public void setOnLoadingProgressChangeLisenter(OnLoadingProgressChangeLisenter l) {
        mOnLoadingProgressChangeLisenter = l;
    }

    public void setOnTitleChange(OnTitleChange l) {
        mOnTitleChange = l;
    }

    public WebViewFk(Context context) {
        super(context);
        init(context);
    }

    public WebViewFk(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WebViewFk(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /*初始化 Web设置*/
    void init(Context context) {
        if (context instanceof IAppFramework)
            ((IAppFramework) context).registeredObserve(mActivityLifeObserve);

        CookieManager.getInstance().setAcceptCookie(true);// 允许网页写入CooKie
        if (getContext() instanceof Activity)
            ((Activity) getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        setDownloadListener(mDownloadListener);

//        setLayerType(View.LAYER_TYPE_SOFTWARE, null); /*关闭硬件加速的canvas ， 硬件加速canvas渲染不支持Chromium WebView   部分手机打开网页黑屏问题*/
        setLayerType(View.LAYER_TYPE_HARDWARE, null); /*开启硬件加速，不然视频黑屏*/
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
        setWebViewClient(mWebViewClient);
        setWebChromeClient(mWebChromeClient);

//        mWebView.addJavascriptInterface(new WebJS(this), "JS调用名称");


        WebSettings settings = getSettings();
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(true);
//        settings.setJavaScriptEnabled(true);
//        settings.setDomStorageEnabled(true);
//        settings.setBlockNetworkImage(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)  /*支持不安全的HTTPS*/
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); /*一些不安全的内容可能被允许被安全的来源加载*/
        settings.setPluginState(WebSettings.PluginState.ON); /*视频相关配置*/

        settings.setRenderPriority(WebSettings.RenderPriority.HIGH); //设置渲染优先级
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setDatabaseEnabled(true);//开启 database storage API 功能 - 需要时配合web开启
        settings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSaveFormData(true);
        settings.setGeolocationEnabled(true); //定位
        settings.setGeolocationDatabasePath(AppUtils.getPackageDataPath() + File.separator + "databases/");

        //配置缓存目录(但是为了适配低版本) - 不推荐使用
        String cacheDirPath = FileCacheConfig.getDefualt().getCacheDir() + File.separator + "webcache";
        settings.setDatabasePath(cacheDirPath);
        settings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录
        settings.setAppCacheEnabled(true); //开启 Application Caches 功能
        settings.setAppCacheMaxSize(5 * 1024 * 1024);
        /*Indexed Database 存储机制  ,  4.4*/


        settings.setUseWideViewPort(true); /*自适应屏幕*/
        settings.setLoadWithOverviewMode(true); //设置网页超过屏幕宽度时重新布局为屏幕宽度
        settings.setDisplayZoomControls(false);
        settings.setBuiltInZoomControls(true); // 设置是否支持缩放
        settings.setSupportZoom(true); // 支持缩放

//        settings.set
//        settings.setTextZoom(85);

        requestFocus();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    /*下载监听*/
    DownloadListener mDownloadListener = new DownloadListener() {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) { /*调用第三方下载*/
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            getContext().startActivity(intent);
        }
    };

    WebViewClient test = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }
    };

    WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (mOnLoadingProgressChangeLisenter != null) {
                mOnLoadingProgressChangeLisenter.onWebStart(view, url, favicon);
                mOnLoadingProgressChangeLisenter.onWebLoading(false, 0);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (mOnTitleChange != null) mOnTitleChange.onTitleChange(getTitle());
            if (mOnLoadingProgressChangeLisenter != null) {
                mOnLoadingProgressChangeLisenter.onWebFinish(view, url);
                mOnLoadingProgressChangeLisenter.onWebLoading(true, 100);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtil.e(MessageFormat.format("重定向检查：{0}", url));
            try {
                if (TextUtils.isEmpty(url)) {

                } else if (URLUtil.isValidUrl(url)) {
                    return super.shouldOverrideUrlLoading(view, url);
                } else {
                    LogUtil.e(MessageFormat.format("特殊 URL： {0}", url));
                    if (url.endsWith(".apk")) { /*下载APK*/
                        getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        return true;
                    } else if (url.startsWith("weixin://")) { /*微信*/
                        getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    } else {
                        getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    }
                }
                return true;
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
            }
            return true;
        }
    };

    WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (mOnLoadingProgressChangeLisenter != null)
                mOnLoadingProgressChangeLisenter.onWebLoading(false, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mOnTitleChange != null) mOnTitleChange.onTitleChange(title);
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) { /*console.log*/
            LogUtil.e(consoleMessage.message());
            return super.onConsoleMessage(consoleMessage);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }
    };


    public void pause() {
        WebViewFk.this.onPause();
        pauseTimers();
    }

    public void resume() {
        WebViewFk.this.onResume();
        resumeTimers();
    }

    /*------------------------- 需要Activity中绑定以下方法 -----------------------------*/
    /*为WebViewFx附加Activity的生命周期*/
    private final ILifeCycleObserve mActivityLifeObserve = new SimpleLifeCycleObserve() {
        @Override
        public void onDestroy() {
            WebViewFk.this.destroy();
        }

        @Override
        public void onResume() {
            resume();
        }

        @Override
        public void onPause() {
            pause();
        }
    };


    /**
     * 加载进度监听
     */
    public interface OnLoadingProgressChangeLisenter {
        void onWebStart(WebView view, String url, Bitmap favicon);

        void onWebLoading(boolean finish, int progress);

        void onWebFinish(WebView view, String url);
    }

    /*当Title变化*/
    public interface OnTitleChange {
        void onTitleChange(String title);
    }
}
