package com.lfp.ardf.util.cache;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;

import com.lfp.ardf.util.AppUtils;

/**
 * <pre>
 * desc:
 *      储存路径相关工具类
 * function:
 *      getRootPath()                  :获取 Android 系统根目录
 *      getDataPath()                  :获取 data 目录
 *      getIntDownloadCachePath()      :获取缓存目录
 *      getAppIntCachePath()           :获取此应用的缓存目录
 *      getAppIntFilesPath()           :获取此应用的文件目录
 *      getAppIntDbPath()              :获取此应用的数据库文件目录
 *      getExtStoragePath()            :获取 Android 外置储存的根目录
 *      getExtAlarmsPath()             :获取闹钟铃声目录
 *      getExtDcimPath()               :获取相机拍摄的照片和视频的目录
 *      getExtDocumentsPath()          :获取文档目录
 *      getExtDownloadsPath()          :获取下载目录
 *      getExtMoviesPath()             :获取视频目录
 *      getExtMusicPath()              :获取音乐目录
 *      getExtNotificationsPath()      :获取提示音目录
 *      getExtPicturesPath()           :获取图片目录
 *      getExtPodcastsPath()           :获取 Podcasts 目录
 *      getExtRingtonesPath()          :获取铃声目录
 *      getAppExtCachePath()           :获取此应用在外置储存中的缓存目录
 *      getAppExtFilePath()            :获取此应用在外置储存中的文件目录
 *      getAppExtAlarmsPath()          :获取此应用在外置储存中的闹钟铃声目录
 *      getAppExtDcimPath()            :获取此应用在外置储存中的相机目录
 *      getAppExtDocumentsPath()       :获取此应用在外置储存中的文档目录
 *      getAppExtDownloadPath()        :获取此应用在外置储存中的闹钟目录
 *      getAppExtMoviesPath()          :获取此应用在外置储存中的视频目录
 *      getAppExtMusicPath()           :获取此应用在外置储存中的音乐目录
 *      getAppExtNotificationsPath()   :获取此应用在外置储存中的提示音目录
 *      getAppExtPicturesPath()        :获取此应用在外置储存中的图片目录
 *      getAppExtPodcastsPath()        :获取此应用在外置储存中的 Podcasts 目录
 *      getAppExtRingtonesPath()       :获取此应用在外置储存中的铃声目录
 *      getObbPath()                   :获取此应用的 Obb 目录
 *      getFilePathByUri()             :获得 uri 对应文件的路径
 *
 * Created by LiFuPing on 2018/6/27.
 * </pre>
 */
public class FilePathUtils {

    private FilePathUtils() {
    }

    /**
     * 获取 Android 系统根目录
     * <pre>path: /system</pre>
     *
     * @return 系统根目录
     */
    public static String getRootPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * 获取 data 目录
     * <pre>path: /data</pre>
     *
     * @return data 目录
     */
    public static String getDataPath() {
        return Environment.getDataDirectory().getAbsolutePath();
    }

    /**
     * 获取缓存目录
     * <pre>path: data/cache</pre>
     *
     * @return 缓存目录
     */
    public static String getIntDownloadCachePath() {
        return Environment.getDownloadCacheDirectory().getAbsolutePath();
    }

    /**
     * 获取此应用的缓存目录
     * <pre>path: /data/data/package/cache</pre>
     *
     * @return 此应用的缓存目录
     */
    public static String getAppIntCachePath() {
        return AppUtils.getApp().getCacheDir().getAbsolutePath();
    }

    /**
     * 获取此应用的文件目录
     * <pre>path: /data/data/package/files</pre>
     *
     * @return 此应用的文件目录
     */
    public static String getAppIntFilesPath() {
        return AppUtils.getApp().getFilesDir().getAbsolutePath();
    }

    /**
     * 获取此应用的数据库文件目录
     * <pre>path: /data/data/package/databases/name</pre>
     *
     * @param name 数据库文件名
     * @return 数据库文件目录
     */
    public static String getAppIntDbPath(String name) {
        return AppUtils.getApp().getDatabasePath(name).getAbsolutePath();
    }

    /**
     * 获取 Android 外置储存的根目录
     * <pre>path: /storage/emulated/0</pre>
     *
     * @return 外置储存根目录
     */
    public static String getExtStoragePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 获取闹钟铃声目录
     * <pre>path: /storage/emulated/0/Alarms</pre>
     *
     * @return 闹钟铃声目录
     */
    public static String getExtAlarmsPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS)
                .getAbsolutePath();
    }

    /**
     * 获取相机拍摄的照片和视频的目录
     * <pre>path: /storage/emulated/0/DCIM</pre>
     *
     * @return 照片和视频目录
     */
    public static String getExtDcimPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                .getAbsolutePath();
    }

    /**
     * 获取文档目录
     * <pre>path: /storage/emulated/0/Documents</pre>
     *
     * @return 文档目录
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getExtDocumentsPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                .getAbsolutePath();
    }

    /**
     * 获取下载目录
     * <pre>path: /storage/emulated/0/Download</pre>
     *
     * @return 下载目录
     */
    public static String getExtDownloadsPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getAbsolutePath();
    }

    /**
     * 获取视频目录
     * <pre>path: /storage/emulated/0/Movies</pre>
     *
     * @return 视频目录
     */
    public static String getExtMoviesPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
                .getAbsolutePath();
    }

    /**
     * 获取音乐目录
     * <pre>path: /storage/emulated/0/Music</pre>
     *
     * @return 音乐目录
     */
    public static String getExtMusicPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
                .getAbsolutePath();
    }

    /**
     * 获取提示音目录
     * <pre>path: /storage/emulated/0/Notifications</pre>
     *
     * @return 提示音目录
     */
    public static String getExtNotificationsPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS)
                .getAbsolutePath();
    }

    /**
     * 获取图片目录
     * <pre>path: /storage/emulated/0/Pictures</pre>
     *
     * @return 图片目录
     */
    public static String getExtPicturesPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .getAbsolutePath();
    }

    /**
     * 获取 Podcasts 目录
     * <pre>path: /storage/emulated/0/Podcasts</pre>
     *
     * @return Podcasts 目录
     */
    public static String getExtPodcastsPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS)
                .getAbsolutePath();
    }

    /**
     * 获取铃声目录
     * <pre>path: /storage/emulated/0/Ringtones</pre>
     *
     * @return 下载目录
     */
    public static String getExtRingtonesPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的缓存目录
     * <pre>path: /storage/emulated/0/Android/data/package/cache</pre>
     *
     * @return 此应用在外置储存中的缓存目录
     */
    public static String getAppExtCachePath() {
        return AppUtils.getApp().getExternalCacheDir().getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的文件目录
     * <pre>path: /storage/emulated/0/Android/data/package/files</pre>
     *
     * @return 此应用在外置储存中的文件目录
     */
    public static String getAppExtFilePath() {
        return AppUtils.getApp().getExternalFilesDir(null).getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的闹钟铃声目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/Alarms</pre>
     *
     * @return 此应用在外置储存中的闹钟铃声目录
     */
    public static String getAppExtAlarmsPath() {
        return AppUtils.getApp().getExternalFilesDir(Environment.DIRECTORY_ALARMS)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的相机目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/DCIM</pre>
     *
     * @return 此应用在外置储存中的相机目录
     */
    public static String getAppExtDcimPath() {
        return AppUtils.getApp().getExternalFilesDir(Environment.DIRECTORY_DCIM)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的文档目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/Documents</pre>
     *
     * @return 此应用在外置储存中的文档目录
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getAppExtDocumentsPath() {
        return AppUtils.getApp().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的闹钟目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/Download</pre>
     *
     * @return 此应用在外置储存中的闹钟目录
     */
    public static String getAppExtDownloadPath() {
        return AppUtils.getApp().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的视频目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/Movies</pre>
     *
     * @return 此应用在外置储存中的视频目录
     */
    public static String getAppExtMoviesPath() {
        return AppUtils.getApp().getExternalFilesDir(Environment.DIRECTORY_MOVIES)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的音乐目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/Music</pre>
     *
     * @return 此应用在外置储存中的音乐目录
     */
    public static String getAppExtMusicPath() {
        return AppUtils.getApp().getExternalFilesDir(Environment.DIRECTORY_MUSIC)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的提示音目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/Notifications</pre>
     *
     * @return 此应用在外置储存中的提示音目录
     */
    public static String getAppExtNotificationsPath() {
        return AppUtils.getApp().getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的图片目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/Pictures</pre>
     *
     * @return 此应用在外置储存中的图片目录
     */
    public static String getAppExtPicturesPath() {
        return AppUtils.getApp().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的 Podcasts 目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/Podcasts</pre>
     *
     * @return 此应用在外置储存中的 Podcasts 目录
     */
    public static String getAppExtPodcastsPath() {
        return AppUtils.getApp().getExternalFilesDir(Environment.DIRECTORY_PODCASTS)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的铃声目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/Ringtones</pre>
     *
     * @return 此应用在外置储存中的铃声目录
     */
    public static String getAppExtRingtonesPath() {
        return AppUtils.getApp().getExternalFilesDir(Environment.DIRECTORY_RINGTONES)
                .getAbsolutePath();
    }

    /**
     * 获取此应用的 Obb 目录
     * <pre>path: /storage/emulated/0/Android/obb/package</pre>
     * <pre>一般用来存放游戏数据包</pre>
     *
     * @return 此应用的 Obb 目录
     */
    public static String getObbPath() {
        return AppUtils.getApp().getObbDir().getAbsolutePath();
    }

    /**
     * 获得 uri 对应文件的路径
     *
     * @param uri uri
     * @return 文件路径
     */
    public static String getFilePathByUri(Uri uri) {
        Context context = AppUtils.getApp();
        String path = null;
        // 以 file:// 开头的
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            path = uri.getPath();
            return path;
        }
        // 以 content:// 开头的，比如 content://media/extenral/images/media/17766
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (columnIndex > -1) {
                        path = cursor.getString(columnIndex);
                    }
                }
                cursor.close();
            }
            return path;
        }
        // 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    // ExternalStorageProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        path = Environment.getExternalStorageDirectory() + "/" + split[1];
                        return path;
                    }
                } else if (isDownloadsDocument(uri)) {
                    // DownloadsProvider
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));
                    path = getDataColumn(context, contentUri, null, null);
                    return path;
                } else if (isMediaDocument(uri)) {
                    // MediaProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    path = getDataColumn(context, contentUri, selection, selectionArgs);
                    return path;
                }
            }
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}
