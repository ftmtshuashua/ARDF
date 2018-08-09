package com.lfp.ardf.util.media.model;

/**
 * <pre>
 * desc:
 *      图片
 *
 * function:
 *
 * Created by LiFuPing on 2018/8/6.
 * </pre>
 */
public class MediaModelPhoto implements MediaModel {
    long id; /*在数据库中的ID*/
    int width;
    int height;
    long size; /*文件大小*/
    String type; /*媒体类型*/
    String data;/*图片地址*/
    String dir_name;/*目录名称*/
    String name; /*文件名称*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDir_name() {
        return dir_name;
    }

    public void setDir_name(String dir_name) {
        this.dir_name = dir_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
