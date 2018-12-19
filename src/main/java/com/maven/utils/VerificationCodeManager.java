package com.maven.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证码管理
 * create by mavenr 2018/12/19
 */
public class VerificationCodeManager {

    // 用户的邮箱或手机号为key， 或者用户首次登录index界面时的cookie的name作为key
    private static Map<String, String> codes = new HashMap<>();

    public static String get(String key) {
        String code = codes.remove(key);
        return code;
    }

    public static void set(String key, String value) {
        codes.put(key, value);
    }
}
