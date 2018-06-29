package com.lfp.ardf.util.cache;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件工具<br/>
 * Created by LiFuPing on 2018/6/26.
 */
public class FileUtils {


    private FileUtils() {
    }

    /**
     * @param fileName
     * @param content
     * @throws Exception
     * @desc 保存内容到文件中
     */
    public static void save(Context context, String fileName, String content, int module) {
        try {
            FileOutputStream os = context.openFileOutput(fileName, module);
            os.write(content.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param fileName
     * @return
     * @desc 读取文件内容
     */
    public static String read(Context context, String fileName) {

        try {
            FileInputStream fis = context.openFileInput(fileName);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = fis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            byte[] data = bos.toByteArray();
            fis.close();
            bos.close();
            return new String(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param context
     * @param fileName
     * @param content
     * @throws IOException
     * @desc 将文本内容保存到sd卡的文件中
     */
    public static void saveToSDCard(Context context, String fileName, String content) throws IOException {

        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content.getBytes());
        fos.close();
    }

    /**
     * @param fileName
     * @return
     * @throws IOException
     * @desc 读取sd卡文件内容
     */
    public static String readSDCard(String fileName) throws IOException {

        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        byte[] data = bos.toByteArray();
        fis.close();
        bos.close();

        return new String(data);
    }

    /*获取缓存路径，存储临时文件，可被一键清理和卸载清理*/
    /*
     * 可以看到，当SD卡存在或者SD卡不可被移除的时候，
     * 就调用getExternalCacheDir()方法来获取缓存路径，
     * 否则就调用getCacheDir()方法来获取缓存路径。
     * 前者获取到的就是/sdcard/Android/data/<application package>/cache 这个路径，
     * 而后者获取到的是 /data/data/<application package>/cache 这个路径。*/
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
