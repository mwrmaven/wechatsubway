package com.maven.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web应用配置
 * @author mavenr
 * create by mavenr 2018/12/19
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

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
