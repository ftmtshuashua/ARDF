package com.lfp.ardf.util.media.provide;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.lfp.ardf.util.media.MediaOption;
import com.lfp.ardf.util.media.model.MediaModel;
import com.lfp.ardf.util.media.model.MediaModelPhoto;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * desc:
 *      SD卡照片读取
 *
 * function:
 *
 * Created by LiFuPing on 2018/8/6.
 * </pre>
 */
public class VideoProvide implements MediaProvide {

    @Override
    public List<? extends MediaModel> query(ContentResolver contentResolver, MediaOption option ) {
        Cursor mCursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Video.Media.DATA
                        , MediaStore.Video.Thumbnails._ID
                        , MediaStore.Video.Media.WIDTH
                        , MediaStore.Video.Media.HEIGHT
                        , MediaStore.Video.Media.SIZE
                        , MediaStore.Video.Media.MIME_TYPE
                        , MediaStore.Video.Media.BUCKET_DISPLAY_NAME
                        , MediaStore.Video.Media.DISPLAY_NAME

                },
                MessageFormat.format("{0} in (?,?,?) And {1}>1000 And {2}>0"
                        , MediaStore.Video.Media.MIME_TYPE
                        , MediaStore.Video.Media.DURATION
                        , MediaStore.Video.Media.SIZE
                ),

                new String[]{"video/mp4", "video/3gpp", "video/3gp"},

                MediaStore.Video.Media.BUCKET_DISPLAY_NAME + " asc"
        );

        List<MediaModelPhoto> arrays = new ArrayList<>();

        if (mCursor != null && mCursor.moveToFirst()) {
            while (mCursor.moveToNext()) {
                MediaModelPhoto photo = new MediaModelPhoto();
                photo.setData(mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DATA)));
                photo.setId(mCursor.getLong(mCursor.getColumnIndex(MediaStore.Video.Thumbnails._ID)));
                photo.setWidth(mCursor.getInt(mCursor.getColumnIndex(MediaStore.Video.Media.WIDTH)));
                photo.setHeight(mCursor.getInt(mCursor.getColumnIndex(MediaStore.Video.Media.HEIGHT)));
                photo.setSize(mCursor.getLong(mCursor.getColumnIndex(MediaStore.Video.Media.SIZE)));
                photo.setType(mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE)));
                photo.setDir_name(mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)));
                photo.setName(mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)));
                arrays.add(photo);
            }
            mCursor.close();
        }
        return arrays;
    }
}
