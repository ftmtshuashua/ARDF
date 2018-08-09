package com.lfp.ardf.util.media;

import android.net.Uri;

import com.lfp.ardf.util.media.provide.MediaProvide;

/**
 * <pre>
 * desc:
 *      媒体加载选项
 *
 * function:
 *
 * Created by LiFuPing on 2018/8/6.
 * </pre>
 */
public class MediaOption {
    /**
     * 查询数据的位置
     */
    Uri content_uri;


    /**
     * 限制读取数据大小的最小值,单位b
     */
    long min_size;

    /**
     * 查询类型
     */
    String[] media_type;

    String selection;


    private MediaOption() {

    }

    public long getMinSize() {
        return min_size;
    }

    public String[] getMediaType() {
        return media_type;
    }

    public String getSelection() {
        return selection;
    }

    public static final class Build {
        MediaProvide provide;
        long min_size;
        String[] media_type;

        public Build setMediaProvide(MediaProvide provide) {
            this.provide = provide;
            return this;
        }

        public Build setMinSize(int min_size) {
            this.min_size = min_size;
            return this;
        }

        public Build setMediaType(String[] media_type) {
            this.media_type = media_type;
            return this;
        }

        public MediaOption build() {
            MediaOption option = new MediaOption();
            option.provide = provide;
            option.min_size = min_size;
            option.media_type = media_type;
            return option;
        }
    }


}
