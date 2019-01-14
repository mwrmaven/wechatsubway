package com.maven.conf;

import com.maven.interceptors.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * web应用配置
 * create by mavenr 2018/12/19
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    /**
     * @author: mawenrui
     * @description: 拦截器配置
     * @param: [registry]
     * @date: 2019-01-10
     * @return: void
    */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加所有的请求拦截器
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**") // 添加要拦截的路径，这里是匹配所有请求路径
                .excludePathPatterns("/static/**")  // 不拦截的路径
                .excludePathPatterns("/login")
                .excludePathPatterns("/registry")
                .excludePathPatterns("/")
                .excludePathPatterns("/error");
    }

    /**
     * @author: mawenrui
     * @description: 跨域配置
     * @param: [registry]
     * @date: 2019-01-10
     * @return: void
    */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }

}
