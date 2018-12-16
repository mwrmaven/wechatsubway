package com.maven.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密算法
 * create by maven 2018/12/14
 */
public class EncryptionUtil {

    private static MessageDigest messageDigest;

    /**
     * 将字节转换成16进制的字符串
     *
     * @param b
     * @return
     */
    public static String byte2Hex(byte[] b) {
        StringBuilder stringBuilder = new StringBuilder();
        String tmp;
        for (int i = 0; i < b.length; i++) {
            tmp = Integer.toHexString(b[i] & 0xFF);
            if (tmp.length() == 1) {
                stringBuilder.append("0");
            }
            stringBuilder.append(tmp);
        }
        return stringBuilder.toString();
    }

    public static MessageDigest initDigest() {
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            return messageDigest;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
