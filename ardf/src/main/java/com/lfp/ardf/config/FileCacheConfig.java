package com.lfp.ardf.config;

import android.content.Context;
import android.os.Environment;

import com.lfp.ardf.util.ApkUtil;

import java.io.File;
import java.io.IOException;

/**
 * 文件缓存配置<br/>
 * Created by LiFuPing on 2018/6/4.
 */
public class FileCacheConfig {
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
    static FileCacheConfig mConfig;

    public FileCacheConfig(File dir_root) {
        PATH_SD_ROOT = dir_root;
    }

    public static final void init(Context c, String dir_name) {
        File PATH_SD_ROOT;
        if (!isSDCardExist()) {
            ApkUtil.init(c);
            PATH_SD_ROOT = new File(ApkUtil.getPackageDataPath());
        } else PATH_SD_ROOT = Environment.getExternalStorageDirectory();

        mConfig = new FileCacheConfig(new File(PATH_SD_ROOT, dir_name));
    }

    public static final void init(File dir_root) {
        mConfig = new FileCacheConfig(dir_root);
    }

    public static final FileCacheConfig getDefualt() {
        return mConfig;
    }

    /*------------------  缓存目录  -----------------*/

    public final File getRootDir() {
        return PATH_SD_ROOT;
    }

    public File getDownloadDir() {
        return verifyDir(new File(getRootDir(), DIR_NAME_DOWNLOAD));
    }

    public File getTemporaryDir() {
        return verifyDir(new File(getRootDir(), DIR_NAME_TEMPORARY));
    }

    public File getCacheDir() {
        return verifyDir(new File(getRootDir(), DIR_NAME_CACHE));
    }

    public File getLogDir() {
        return verifyDir(new File(getRootDir(), DIR_NAME_LOG));
    }

    private File verifyDir(File dir) {
        if (!dir.exists()) dir.mkdirs();
        return dir;
    }

    /*检查SD卡是否可用*/
    public static boolean isSDCardExist() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


    /**
     * 创建文件或文件夹<br/>
     * 如果目标文件夹不存在会根据配置创建默认文件夹
     *
     * @param files 文件或文件夹
     * @return return true 表示成功
     * @throws IOException 创建文件失败
     */
    public static boolean createNewFile(File... files) throws IOException {
        if (!isSDCardExist()) return false;
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
