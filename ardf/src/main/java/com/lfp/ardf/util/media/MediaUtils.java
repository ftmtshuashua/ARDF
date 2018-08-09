package com.lfp.ardf.util.media;

import android.content.ContentResolver;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.util.AppUtils;
import com.lfp.ardf.util.constant.MemoryConstants;
import com.lfp.ardf.util.media.model.MediaModel;
import com.lfp.ardf.util.media.provide.MediaProvide;
import com.lfp.ardf.util.media.provide.PhotoProvide;
import com.lfp.ardf.util.media.provide.VideoProvide;

import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.List;

/**
 * <pre>
 * desc:
 *      相机/相册/录像工具
 *
 * function:
 *
 * Created by LiFuPing on 2018/7/27.
 * </pre>
 */
public class MediaUtils {

    public static final void test() {
        getPhoto();
    }

    /**
     * 查询媒体数据
     *
     * @param option
     * @return
     */
    public static final List<? extends MediaModel> query(@NonNull MediaProvide provide, @NonNull MediaOption option) {
        return provide.query(getContentResolver(), option);
    }


    public static final List<? extends MediaModel> getPhoto() {
        return MediaUtils.query(new PhotoProvide()
                , new MediaOption.Build()
                        .setMediaType(new String[]{"image/jpeg", "image/png"})
                        .setMinSize(MemoryConstants.KB * 100)
                        .build()
        );
    }

    public static final List<? extends MediaModel> getVideo() {
        return MediaUtils.query(new VideoProvide()
                , new MediaOption.Build()
                        .setMediaType(new String[]{"video/mp4", "video/3gpp", "video/3gp"})
                        .setMinSize(MemoryConstants.KB * 100)
                        .build()
        );
    }

    static ContentResolver mContentResolver;

    private static ContentResolver getContentResolver() {
        if (mContentResolver == null)
            synchronized (MediaUtils.class) {
                if (mContentResolver == null) {
                    mContentResolver = AppUtils.getApp().getContentResolver();
                }
            }
        return mContentResolver;
    }

    public static final void log(Cursor mCursor) {
        if (mCursor != null && mCursor.moveToFirst()) {
            try {
                int index = 0;
                while (mCursor.moveToNext()) {
                    JSONObject JO = new JSONObject();
                    int count = mCursor.getColumnCount();
                    for (int i = 0; i < count; i++) {
                        String key = mCursor.getColumnName(i);
                        String value = mCursor.getString(i);
                        JO.put(key, value);
                    }
                    LogUtil.e(MessageFormat.format("{0} - {1}", index, JO));
                    index++;
                }
            } catch (Exception e) {
            }
        }

    }


    protected static String getSelection(MediaOption option) {


        if (null != option.getMediaType()) {

        }
        if (option.getMinSize() > 0) {

        }


        return "";
    }

}
