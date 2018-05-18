package com.lfp.ardf.cryptography;

import java.nio.charset.Charset;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Encoder;

/**
 * Created by LiFuPing on 2018/5/18.
 */
public class Des3EncodeCBC {

    /**
     * 编码
     *
     * @param key_str
     * @param keyiv_str
     * @param data_str
     * @return
     * @throws Exception
     */
    public static String encodeCBC(String key_str, String keyiv_str, String data_str) throws Exception {
        Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
        byte[] key = key_str.getBytes(DEFAULT_CHARSET);
        byte[] keyiv = keyiv_str.getBytes(DEFAULT_CHARSET);
        byte[] data = data_str.getBytes(DEFAULT_CHARSET);

        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");

        Key deskey = keyfactory.generateSecret(spec);
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        return new BASE64Encoder().encode(cipher.doFinal(data));
    }

    public static void main(String[] args) throws Exception {
        String str = "明文";
        String _key = "9c69661a8fa90b6c095fde26551e4456";
        String _iv = "8fa90b6c";
        System.out.println(encodeCBC(_key, _iv, str));
    }
}
