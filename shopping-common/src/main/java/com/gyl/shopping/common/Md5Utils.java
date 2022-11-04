package com.gyl.shopping.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: gyl
 * @Description: Md5加密
 */
public class Md5Utils {
    public static String getMd5Str (String str) {
        MessageDigest md5 = null;
        String md5Str = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest((str + "8svbsvjkweDF,.03[").getBytes(StandardCharsets.UTF_8));
            md5Str = new String(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new MallException(e.hashCode(), e.getMessage());
        }
        return md5Str;
    }
}
