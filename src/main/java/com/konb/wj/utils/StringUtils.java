package com.konb.wj.utils;

import java.util.Base64;

/**
 * @author konb
 */
public class StringUtils {

    /**
     * Base64加密路径
     * @param url 原路径
     * @return String
     */
    public static String base64UrlEncode(String url) {
        return Base64.getUrlEncoder().encodeToString(url.getBytes());
    }

    /**
     * Base64解密密文
     * @param cipherText 密文
     * @return String
     */
    public static String base64UrlDecoder(String cipherText) {
        return new String(Base64.getUrlDecoder().decode(cipherText));
    }

    /**
     * Base64加密字符串
     * @param str 原字符串
     * @return String
     */
    public static String base64Encode(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    /**
     * Base64解密密文
     * @param cipherText 密文
     * @return String
     */
    public static String base64Decoder(String cipherText) {
        return new String(Base64.getDecoder().decode(cipherText));
    }

}
