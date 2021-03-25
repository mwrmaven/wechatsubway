package com.maven.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 解析请求的ip地址的工具
 * @author mavenr
 * create by maven 2018/12/14
 */
@Slf4j
public class IpUtil {

    public static String parseIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        log.info("X-Forwarded-For's ip : {}", ip);
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            log.info("roxy-Client-IP's ip : {}", ip);
            return ip;
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.info("WL-Proxy-Client-IP's ip : {}", ip);
            return ip;
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.info("HTTP_CLIENT_IP's ip : {}", ip);
            return ip;
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.info("HTTP_X_FORWARDED_FOR's ip : {}", ip);
            return ip;
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            log.info("remoteAddr's ip : {}", ip);
            return ip;
        }
        return ip;
    }
}
