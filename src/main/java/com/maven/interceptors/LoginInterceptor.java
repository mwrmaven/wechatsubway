package com.maven.interceptors;

import com.maven.entity.CommonEntity;
import com.maven.utils.CookieManager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * 登录的拦截器
 * create by mavenr 2018/12/19
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 处理请求之前触发
     * @param request
     * @param response
     * @param handler
     * @return
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 获取cookie，name为用户唯一生成的id，value为失效时间
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            pw.write(new CommonEntity(203).toString());
            return false;
        }

        for (Cookie c : cookies) {
            String key = c.getName();
            String value = c.getValue();

            if (CookieManager.get(key) != null) {
                if (Long.parseLong(value) > new Date().getTime()) {
                    return true;
                }
                // 被拦截之后，返回数据给客户端
                pw.write(new CommonEntity(203).toString());
                return false;
            }
            pw.write(new CommonEntity(203).toString());
            return false;
        }
        pw.write(new CommonEntity(203).toString());
        return false;
    }
}
