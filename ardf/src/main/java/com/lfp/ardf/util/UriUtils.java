package com.lfp.ardf.util;

import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * <pre>
 * desc:
 *      Uri类相关工具
 * function:
 *      getUriForFile(): 将文件转为Uri
 * Created by LiFuPing on 2018/6/29.
 * </pre>
 */
public class UriUtils {
    private UriUtils() {
    }

    /**
     * Return a content URI for a given file.
     *
     * @param file The file.
     * @return a content URI for a given file
     */
    public static Uri getUriForFile(final File file) {
        if (file == null) return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authority = AppUtils.getPackageName() + ".utilcode.provider";
            return FileProvider.getUriForFile(AppUtils.getApp(), authority, file);
        } else {
            return Uri.fromFile(file);
        }
    }
}
