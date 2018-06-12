package com.lfp.androidrapiddevelopmentframework.api;

import com.lfp.ardf.module.net_deprecated.OkHttpRequest;
import com.lfp.ardf.module.net_deprecated.util.UrlFormat;

/**
 * 接口文档
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
     * 获取天气预报信息<br/>
     * http://tj.nineton.cn/Heart/index/all?city=CHSH000000&language=zh-chs&unit=c&aqi=city&alarm=1&key=78928e706123c1a8f1766f062bc8676b<br/>
     * http://www.weather.com.cn/data/cityinfo/101190408.html
     * 感谢<中央天气预报>
     */
    public static OkHttpRequest getWeatherForecast() {
        UrlFormat format = new UrlFormat("http://www.weather.com.cn/data/cityinfo/101190408.html");
        format.addQuery("city", "CHSH000000");
        return new OkHttpRequest(format.toUrl());
    }

    public static com.lfp.ardf.module.net.OkHttpRequest getNewRequest(){
        UrlFormat format = new UrlFormat("http://www.weather.com.cn/data/cityinfo/101190408.html");
        return new com.lfp.ardf.module.net.OkHttpRequest(format.toUrl());
    }
}
