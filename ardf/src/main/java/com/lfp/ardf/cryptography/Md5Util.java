package com.lfp.ardf.cryptography;

import java.security.MessageDigest;

/**
 * Md5 工具<br>
 * Created by LiFuPing on 2018/6/1.
 */
public class Md5Util {

    /**
     * MD5 加密字符串
     * @param str String
     * @return String
     */
    public static String toMd5(String str) {
        try {
            return toMd5_32(str.getBytes("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 32位MD5
     * @param data byte[]
     * @return String
     */
    public static String toMd5_32(byte[] data) {
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(data);
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10) hex.append("0");
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 64位MD5
     * @param data byte[]
     * @return String
     */
    public static String toMd5_16(byte[] data) {
        return toMd5_32(data).substring(8, 24);
    }

}
