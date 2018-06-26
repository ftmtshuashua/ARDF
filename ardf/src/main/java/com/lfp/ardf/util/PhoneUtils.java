package com.lfp.ardf.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import com.lfp.ardf.cryptography.Md5Util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 手机设备信息<br>
 * Created by LiFuPing on 2018/6/1.
 */
public class PhoneUtils {


    static Context mContext;

    private PhoneUtils() {
    }

    public static final void init(Context c) {
        mContext = c.getApplicationContext();
    }


    /**
     * 设备厂商
     *
     * @return String
     */
    public static String getPhoneBrand() {
        return Build.BOARD + "  " + Build.MANUFACTURER;
    }

    /**
     * 设备名称
     *
     * @return String
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * 打开评分系统
     */
    public static void jumpComments() {
        Uri uri = Uri.parse("market://details?id=" + mContext.getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 判断微信是否可用
     *
     * @param context Context
     * @return boolean
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断qq是否可用
     *
     * @param context Context
     * @return boolean
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     *  获得手机唯一标记
     * @return String
     */
    public static String getPhoneOnlyTag() {
        ArrayList<String> data = new ArrayList<>();
        data.add(Build.BOARD);
        data.add(Build.BRAND);
        data.add(Build.CPU_ABI);
        data.add(Build.DEVICE);
        data.add(Build.DISPLAY);
        data.add(Build.HOST);
        data.add(Build.ID);
        data.add(Build.MANUFACTURER);
        data.add(Build.MODEL);
        data.add(Build.PRODUCT);
        data.add(Build.TAGS);
        data.add(Build.TYPE);
        data.add(Build.USER);
        return Md5Util.toMd5(ArrayUtils.join(data, "-"));
    }

    /**
     * 获得设备信息
     *
     * @return String
     */
    public static String getPhoneInfo() {
        return MessageFormat.format("设备厂商:{0}\n设备名称:{1}\nAPI:{2} {3}", getPhoneBrand(), getPhoneModel(), SdkUtiles.getSdkVersion(), SdkUtiles.getSdkVersionName());
    }
}
