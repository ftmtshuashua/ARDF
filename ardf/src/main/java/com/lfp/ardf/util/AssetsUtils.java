package com.lfp.ardf.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;

/**
 * Assets信息获取工具<br>
 * Created by LiFuPing on 2018/6/4.
 */
public class AssetsUtils {
    static Context mContext;

    public static final void init(Context c) {
        mContext = c.getApplicationContext();
    }

    public static AssetManager getAssets() {
        return mContext.getResources().getAssets();
    }


    /**
     * 取得assets中文件的内容<br>
     * 例：文件名.txt<br>
     * 例：文件目录/文件名.txt
     *
     * @param fileName 文件名
     * @return 文件内容
     */
    public  static String getString(String fileName) {
        String result = "";
        try {
            InputStream in = getAssets().open(fileName);
            int lenght = in.available();
            byte[] buffer = new byte[lenght];
            in.read(buffer);
            result = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
