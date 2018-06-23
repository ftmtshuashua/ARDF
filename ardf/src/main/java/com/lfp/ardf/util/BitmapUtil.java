package com.lfp.ardf.util;

import android.graphics.Bitmap;

/**
 * 图片工具<br>
 * 图片压缩
 * <p>
 * Created by LiFuPing on 2018/6/20.
 */
public class BitmapUtil {

    private void zip(int imgId, Bitmap bitmap, int heightNode) {
//        Matrix matrix = new Matrix();
//        int reqWidth = getWidth() - mPadding - mPadding;
//        int reqHeight = getHeight() - mPadding - mPadding;
//        if (reqHeight <= 0 || reqWidth <= 0) return;
//        int rawWidth = 0;
//        int rawHeight = 0;
//        if (imgId != 0 && bitmap == null) {
//            WeakReference<BitmapFactory.Options> weakOptions = new WeakReference<BitmapFactory.Options>(new BitmapFactory.Options());
//            if (weakOptions.get() == null) return;
//            BitmapFactory.Options options = weakOptions.get();
//            BitmapFactory.decodeResource(getResources(), imgId, options);
//            options.inJustDecodeBounds = true;
//            rawWidth = options.outWidth;
//            rawHeight = options.outHeight;
//            options.inSampleSize = calculateInSampleSize(rawWidth, rawHeight, getWidth() - mPadding * 2, getHeight() - mPadding * 2);
//            options.inJustDecodeBounds = false;
//            bitmap = BitmapFactory.decodeResource(getResources(), mImgId, options);
//        } else if (imgId == 0 && bitmap != null) {
//            rawWidth = bitmap.getWidth();
//            rawHeight = bitmap.getHeight();
//            float scale = rawHeight * 1.0f / rawWidth;
//            mRealBitmap = Bitmap.createScaledBitmap(bitmap, reqWidth, (int) (reqWidth * scale), true);
//            initShadow(mRealBitmap);
//            return;
//        }
//        if (heightNode == 0) {
//            float scale = rawHeight * 1.0f / rawWidth;
//            mRealBitmap = Bitmap.createScaledBitmap(bitmap, reqWidth, (int) (reqWidth * scale), true);
//        } else {
//            int dx = 0;
//            int dy = 0;
//            int small = Math.min(rawHeight, rawWidth);
//            int big = Math.max(reqWidth, reqHeight);
//            float scale = big * 1.0f / small;
//            matrix.setScale(scale, scale);
//            if (rawHeight > rawWidth) {
//                dy = (rawHeight - rawWidth) / 2;
//            } else if (rawHeight < rawWidth) {
//                dx = (rawWidth - rawHeight) / 2;
//            }
//            if (small <= 0) return;
//            mRealBitmap = Bitmap.createBitmap(bitmap, dx, dy, small, small, matrix, true);
//        }
//        initShadow(mRealBitmap);

    }


}
