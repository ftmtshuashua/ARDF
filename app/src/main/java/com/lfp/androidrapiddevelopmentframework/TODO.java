package com.lfp.androidrapiddevelopmentframework;

/**
 * <br>
 * Created by LiFuPing on 2018/6/4.
 */
public class TODO {

    /*
    已开发功能
    BaseRecyclerViewAdapter
    FileCacheConfig
    FragmentControl
    RadioGroupControl
    LogUtil
    BaseDialog
    ILifeCycleObserved
    NotProguard
    并发请求/链式请求
    BaseProgressBarView
    DrawableCenterTextView
    WebViewFx
    其他工具包

    待开发功能
    1.Banner
    2.上下滚动公告控件
    3.WebView
    4.MaskLayer
    5.EventBus
    6.刷新与加载
    7.视频
    8.相机
    9.播放
    10.视频音频/裁剪
    11.断点下载/上传
    12.Notification




     */


    public static final void main(String... arg) {

        for (double i = 0; i <= 100; i++) {
            double value = wight(i / 100, 100);
//            double value = Math.sin(i * Math.PI / 180);
            System.err.println(String.format("%s = %s", i, value));
        }

    }

    private static final double wight(double bl, double maxwight) {
        double jd = bl * Math.PI ;
        return Math.sin(jd) * maxwight;
    }

}
