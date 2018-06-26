package com.lfp.ardf.util.cache;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * SD卡管理工具<br/>
 * 1.sd卡存在查询
 * 2.sd卡容量查询
 * 2.读写文件
 * <p>
 * <p>
 * Created by LiFuPing on 2018/6/26.
 */
public class SDCardUtils {

    /**
     * 检查用户手机中是否存在SD卡
     *
     * @return SD卡是否存在
     */
    public static boolean isExist() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获得SD卡根目录
     *
     * @return 根目录
     */
    public static File getRootPath() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 获取SD卡的剩余容量 单位byte
     */
    public static long getCapacity() {
        if (isExist()) {
            StatFs stat = new StatFs(getRootPath().getAbsolutePath());
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

}
