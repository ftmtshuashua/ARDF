package com.lfp.androidrapiddevelopmentframework;


/**
 * <br>
 * Created by LiFuPing on 2018/6/4.
 */
public class TODO {

    /*
    已开发功能
    BaseRecyclerViewAdapter

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

        int[] array = new int[]{4, 0, 2, 4, 4, 3};
        int length = removeDuplicates(array);
        System.err.println("length:" + length);
        for (int i = 0; i < length; i++) {
            System.err.println(array[i]);
        }
    }


    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int length = nums.length - 1;
        int index = 0;
        for (int i = 0; i < length; i++) {
            int value = nums[i + 1];
            if (nums[index] != value) {
                nums[++index] = value;
            }
        }
        return index + 1;
    }


    public static int removeDuplicates2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] != nums[i]) {
                nums[count++] = nums[i];
            }
        }
        return count;
    }
}
