package com.lfp.ardf.module.net.util;

import android.text.TextUtils;
import android.webkit.URLUtil;

import com.lfp.ardf.util.ArrayUtil;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Url格式化<br/>
 * protocol     host        port      path                      file               query
 * http://   www.xxx.com    :80     /path.path_path    /   demo_file.html    ?   key=value
 * <p>
 * Created by LiFuPing on 2017/7/21.
 */
public class UrlFormat {
    String originalUrl; //原始URL

    String protocol;
    String host;
    String port;
    String path;
    String file;
    HashMap<String, String> query = new HashMap<>(); //Query 列表


    /**
     * URL 格式化
     *
     * @param url url
     */
    public UrlFormat(String url) {
        this(url, false);
    }

    /**
     * URL 格式化
     *
     * @param url    原始URL
     * @param fixurl 对于一些错误的URL进行修复
     */
    public UrlFormat(String url, boolean fixurl) {
        originalUrl = url;
        if (fixurl) url = URLUtil.guessUrl(url);
        if (url.contains("?")) {
            String[] urlformat = url.split("\\?", 2);
            path = urlformat[0];
            HashMap<String, String> Params = parseQuery(urlformat[1]);
            Iterator<String> keys = Params.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                query.put(key, Params.get(key));
            }
        } else path = url;
    }

    /**
     * 不覆盖添加URL Query
     */
    public UrlFormat addQuery(String key, Object value) {
        if (!TextUtils.isEmpty(query.get(key))) return this;
        String tag;
        if (value == null) {
            tag = "";
        } else if (value instanceof Number) {
            tag = MessageFormat.format("{0,number,0.#############}", value);
        } else if (value instanceof String) {
            tag = (String) value;
        } else {
            tag = value.toString();
        }
        query.put(key, tag);
        return this;
    }

    /**
     * 移除URL Query
     */
    public void removeQuery(String key) {
        query.remove(key);
    }

    /**
     * 覆盖添加URL Query
     */
    public UrlFormat setQuery(String key, Object value) {
        if (!TextUtils.isEmpty(query.get(key))) removeQuery(key);
        addQuery(key, value);
        return this;
    }

    /**
     * 获得URL Query
     */
    public HashMap<String, String> getQueryMap() {
        return query;
    }

    /**
     * 获得参数
     */
    public String getQuery() {
        return query.isEmpty() ? "" : ArrayUtil.join(getQueryMap(), "&", "=");
    }

    /**
     * 获得Url编码之后的参数
     */
    public String getEncodeQuery() {
        if (query.isEmpty()) return "";
        HashMap<String, String> end_query = new HashMap<>();
        HashMap<String, String> query_array = getQueryMap();
        Iterator<String> keys = query_array.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = query_array.get(key);
            end_query.put(key, URLEncoder.encode(value));
        }
        return ArrayUtil.join(end_query, "&", "=");
    }


    /**
     * 获得原始URL
     */
    public String getOriginalUrl() {
        return originalUrl;
    }

    /**
     * 生成URL
     */
    public String toUrl() {
        if (query.isEmpty()) return path;
        return MessageFormat.format("{0}?{1}", path, getQuery());
    }

    /**
     * 生成URL并且将query中value值进行编码
     */
    public String toEncodeUrl() {
        if (query.isEmpty()) return path;
        return MessageFormat.format("{0}?{1}", path, getEncodeQuery());
    }


    @Override
    public String toString() {
        return toUrl();
    }

    /**
     * 获得Url中的Query
     */
    public static HashMap<String, String> parseQuery(String url) {
        HashMap<String, String> data = new HashMap<>();
        String[] params = url.split("&");
        for (String str : params) {
            if (TextUtils.isEmpty(str)) continue;
            try {
                String[] parmas = str.split("=", 2);
                String key = null;
                String value = null;
                if (parmas.length == 1) {
                    key = parmas[0];
                } else if (parmas.length == 2) {
                    key = parmas[0];
                    value = parmas[1];
                } else if (parmas.length > 2) {
                    key = parmas[0];
                    StringBuffer SB = new StringBuffer();
                    for (int i = 1; i < parmas.length; i++) {
                        SB.append("=").append(parmas[i]);
                    }
                    if (SB.length() != 0) SB.deleteCharAt(0);
                    value = SB.toString();
                }
                if (!TextUtils.isEmpty(key) && value != null) data.put(key, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

}
