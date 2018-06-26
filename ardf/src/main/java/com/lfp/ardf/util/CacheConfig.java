package com.lfp.ardf.util;

import android.content.Context;

import java.io.File;
import java.io.IOException;

/**
 * 文件缓存配置<br>
 * 如果SD卡可用，缓存目录为SD卡下{@code PATH_SD_ROOT}<br>
 * 如果SKD卡不可用，缓存目录为/data/data/包/{@code PATH_SD_ROOT}<br>
 * 自定义目录除外<br>
 * Created by LiFuPing on 2018/6/4.
 */
public class CacheConfig {
    /**
     * 缓存目录文件名
     */
    public static final String DIR_NAME_CACHE = "cache";
    /**
     * 日志目录文件名
     */
    public static final String DIR_NAME_LOG = "log";
    /**
     * 下载缓存目录文件名
     */
    public static final String DIR_NAME_DOWNLOAD = "download";
    /**
     * 临时缓存目录文件名
     */
    public static final String DIR_NAME_TEMPORARY = "temporary";

    /**
     * 缓存根目录
     */
    private File PATH_SD_ROOT;

    /**
     * App统一配置
     */
    static CacheConfig mConfig;

    public CacheConfig(File dir_root) {
        PATH_SD_ROOT = dir_root;
    }

    /**
     * 初始化缓存目录配置
     *
     * @param c             传入Applicaion
     * @param main_dir_name 主目录名字
     */
    public static final void init(Context c, String main_dir_name) {
        File PATH_SD_ROOT;
        if (!SDCardUtils.isExist()) {
            ApkUtils.init(c);
            PATH_SD_ROOT = new File(ApkUtils.getPackageDataPath());
        } else PATH_SD_ROOT = SDCardUtils.getRootPath();

        mConfig = new CacheConfig(new File(PATH_SD_ROOT, main_dir_name));
    }

    public static final void init(File dir_root) {
        mConfig = new CacheConfig(dir_root);
    }

    public static final CacheConfig getDefualt() {
        return mConfig;
    }

    /*------------------  缓存目录  -----------------*/
    public final File getRootDir() {
        return PATH_SD_ROOT;
    }

    public File getDownloadDir() {
        return getDir(DIR_NAME_DOWNLOAD);
    }

    public File getTemporaryDir() {
        return getDir(DIR_NAME_TEMPORARY);
    }

    public File getCacheDir() {
        return getDir(DIR_NAME_CACHE);
    }

    public File getLogDir() {
        return getDir(DIR_NAME_LOG);
    }

    public File getDir(String dir_name) {
        return verifyDir(new File(getRootDir(), dir_name));
    }

    private File verifyDir(File dir) {
        if (!dir.exists()) dir.mkdirs();
        return dir;
    }


    /**
     * 创建文件或文件夹<br>
     * 如果目标文件夹不存在会根据配置创建默认文件夹
     *
     * @param files 文件或文件夹
     * @return return true 表示成功
     * @throws IOException 创建文件失败
     */
    public static boolean createNewFile(File... files) throws IOException {
        if (!SDCardUtils.isExist()) return false;
        for (File file : files) {
            if (!file.exists()) file.mkdirs();

            if (!file.isDirectory()) {
                if (file.exists()) file.delete();
                file.createNewFile();
            }
        }
        return true;
    }

}
