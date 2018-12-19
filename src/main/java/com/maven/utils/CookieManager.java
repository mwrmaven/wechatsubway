package com.maven.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * cookie管理
 * create by mavenr 2018/12/19
 */
public class CookieManager {

    // map中为cookie和失效时间
    private static Map<Object, Long> cookies = new HashMap<>();

    public static Long get(Object key) {
        return cookies.get(key);
    }

    public static void set(Object key, Long value) {
        cookies.put(key, value);
    }

    public static void delete(Object key) {
        cookies.remove(key);
    }
}
