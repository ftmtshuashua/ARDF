package com.lfp.ardf.util.media.provide;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.lfp.ardf.util.constant.MemoryConstants;
import com.lfp.ardf.util.media.MediaOption;
import com.lfp.ardf.util.media.MediaUtils;
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
public class PhotoProvide implements MediaProvide {


    @Override
    public List<? extends MediaModel> query(ContentResolver contentResolver, MediaOption option) {
        Cursor mCursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Images.Media.DATA
                        , MediaStore.Images.Thumbnails._ID
                        , MediaStore.Images.Media.WIDTH
                        , MediaStore.Images.Media.HEIGHT
                        , MediaStore.Images.Media.SIZE
                        , MediaStore.Images.Media.MIME_TYPE
                        , MediaStore.Images.Media.BUCKET_DISPLAY_NAME
                        , MediaStore.Images.Media.DISPLAY_NAME
                },
                MessageFormat.format("{0} IN (?,?) And {1}>={3,number,0} And {2} not like ?"
                        , MediaStore.Images.Media.MIME_TYPE
                        , MediaStore.Images.Media.SIZE
                        , MediaStore.Images.Media.DISPLAY_NAME
                        , MemoryConstants.KB * 100
                ),

                new String[]{"image/jpeg", "image/png", "thumb%"}
                , MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " asc"
        );

        List<MediaModelPhoto> arrays = new ArrayList<>();

        MediaUtils.log(mCursor);

        if (mCursor != null && mCursor.moveToFirst()) {
            while (mCursor.moveToNext()) {
                MediaModelPhoto photo = new MediaModelPhoto();
                photo.setData(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                photo.setId(mCursor.getLong(mCursor.getColumnIndex(MediaStore.Images.Thumbnails._ID)));
                photo.setWidth(mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.WIDTH)));
                photo.setHeight(mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.HEIGHT)));
                photo.setSize(mCursor.getLong(mCursor.getColumnIndex(MediaStore.Images.Media.SIZE)));
                photo.setType(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE)));
                photo.setDir_name(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)));
                photo.setName(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
                arrays.add(photo);
            }
            mCursor.close();
        }


        return arrays;
    }
}
