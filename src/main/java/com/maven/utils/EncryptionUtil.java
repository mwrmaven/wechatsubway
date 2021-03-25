package com.maven.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密算法
 * @author mavenr
 * create by maven 2018/12/14
 */
public class EncryptionUtil {

    /**
     * SHA加密算法
     * @return
     */
    public static String sha(String key) {
        StringBuilder stringBuilder = new StringBuilder();
        String tmp;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(key.getBytes());
            byte[] digest = messageDigest.digest();

            for (int i = 0; i < digest.length; i++) {
                tmp = Integer.toHexString(digest[i] & 0xFF);
                if (tmp.length() == 1) {
                    stringBuilder.append("0");
                }
                stringBuilder.append(tmp);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MD5加密
     * @param key
     * @return
     */
    public static String md5(String key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(key.getBytes());
            StringBuilder tmp = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                int bt = bytes[i]&0xff;
                if (bt < 16) {
                    tmp.append(0);
                }
                tmp.append(Integer.toHexString(bt));
            }
            return tmp.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
