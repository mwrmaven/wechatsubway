package com.maven.conf;

import com.maven.interceptors.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 配置拦截器等信息
 * create by mavenr 2018/12/19
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        // 添加所有的请求拦截器
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(loginInterceptor);
        // 添加要拦截的路径，这里是匹配所有请求路径
        interceptorRegistration.addPathPatterns("/**");
        // 不拦截的路径
        interceptorRegistration.excludePathPatterns("/static/**");
    }
}
