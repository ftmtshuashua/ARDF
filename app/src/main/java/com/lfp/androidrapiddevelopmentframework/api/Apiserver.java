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

    private Apiserver() {
    }

    /*测试请求*/
    public static OkHttpRequest getNewRequest() {
        UrlFormat format = new UrlFormat("http://www.wanandroid.com/tools/mockapi/6616/test");
        return new OkHttpRequest(format.toUrl());
    }

    /*获得版本信息*/
    public static OkHttpRequest getVersion() {
        UrlFormat format = new UrlFormat("http://www.wanandroid.com/tools/mockapi/6768/ardf_version_android");
        return new OkHttpRequest(format.toUrl());
    }

    /* 获得大厅Banner数据 */
    public static OkHttpRequest getBanner() {
        UrlFormat format = new UrlFormat("http://www.wanandroid.com/tools/mockapi/6768/ardf_banner");
        return new OkHttpRequest(format.toUrl());
    }
}
