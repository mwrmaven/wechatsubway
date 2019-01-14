package com.maven.utils;

import java.util.HashMap;
import java.util.Iterator;
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

    public static boolean set(Object key, Long value) {
        try {
            cookies.put(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean delete(Object key) {
        Iterator<Map.Entry<Object, Long>> iterator = cookies.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Object, Long> entry = iterator.next();
            if (entry.getKey().equals(key)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}
