package com.lfp.androidrapiddevelopmentframework.api;


import com.lfp.ardf.module.net.OkHttpRequest;
import com.lfp.ardf.module.net.util.UrlFormat;

/**
 * 接口文档
 * <p>
 * 感谢:WAN ANDROID 提供自定义API
 * http://www.wanandroid.com/tools/mockapi
 * <p>
 * <p>
 * Created by LiFuPing on 2018/5/17.
 */
public class Apiserver {
    static Apiserver mInstace;

    private Apiserver() {
    }

    public static final Apiserver getInstace() {
        if (mInstace == null) {
            synchronized (Apiserver.class) {
                if (mInstace == null) mInstace = new Apiserver();
            }
        }
        return mInstace;
    }


    /**
     * 获得大厅Banner数据
     *
     * @return
     */
    public static OkHttpRequest getHallBanner() {
        UrlFormat format = new UrlFormat("http://www.wanandroid.com/tools/mockapi/6616/hallBanner");
        return new OkHttpRequest(format.toUrl());
    }

    public static OkHttpRequest getNewRequest() {
        UrlFormat format = new UrlFormat("http://www.wanandroid.com/tools/mockapi/6616/test");
        return new OkHttpRequest(format.toUrl());
    }

    public static OkHttpRequest getVersion() {
        UrlFormat format = new UrlFormat("http://www.wanandroid.com/tools/mockapi/6768/version_android");
        return new OkHttpRequest(format.toUrl());
    }
}
