package com.lfp.ardf.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.lfp.ardf.debug.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * desc:
 *      Assets目录访问工具
 *
 * function:
 *
 * Created by LiFuPing on 2018/7/26.
 * </pre>
 */
public class AssetsDirUtils {


    public static List<String> getAssetPicPath() {
        AssetManager am = AppUtils.getApp().getAssets();
        String[] path = null;
        try {
            path = am.list("");  // ""获取所有,填入目录获取该目录下所有资源
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            am.close();
        }

        List<String> pciPaths = new ArrayList<>();
        for (int i = 0; i < path.length; i++) {
            if (path[i].endsWith(".png") && path[i].startsWith("ic_")) {  // 根据图片特征找出图片
                pciPaths.add(path[i]);
            }
        }
        return pciPaths;
    }

    /**
     * 根据路径获取Bitmap图片
     *
     * @param context
     * @param path
     * @return
     */
    public static Bitmap getAssetsBitmap(Context context, String path) {
        AssetManager am = context.getAssets();
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            inputStream = am.open(path);
        } catch (IOException e) {
            LogUtil.e(e);
        }
        if (inputStream != null) bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
}

