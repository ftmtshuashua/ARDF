package com.lfp.ardf.cryptography;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5 工具<br/>
 * Created by LiFuPing on 2018/6/1.
 */
public class Md5Util {


    /**
     * MD5 加密字符串
     *
     * @param string
     * @return
     */
    public static String toMd5(String string){
        byte[] hash;
        try {
            hash = MessageDigest.getInstance ("MD5").digest (string.getBytes ("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException ("Huh, MD5 should be supported?",e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException ("Huh, UTF-8 should be supported?",e);
        }

        StringBuilder hex = new StringBuilder (hash.length * 2);
        for ( byte b : hash ) {
            if ((b & 0xFF) < 0x10) hex.append ("0");
            hex.append (Integer.toHexString (b & 0xFF));
        }
        return hex.toString ();
    }


}
