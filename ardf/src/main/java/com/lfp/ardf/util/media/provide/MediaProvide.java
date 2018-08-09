package com.lfp.ardf.util.media.provide;

import android.content.ContentResolver;

import com.lfp.ardf.util.media.MediaOption;
import com.lfp.ardf.util.media.model.MediaModel;

import java.util.List;

/**
 * <pre>
 * desc:
 *      数据提供器,负责具体数据的获取
 *
 * function:
 *
 * Created by LiFuPing on 2018/8/6.
 * </pre>
 */
public interface MediaProvide {

    List<? extends MediaModel> query(ContentResolver contentResolver, MediaOption option);


}
