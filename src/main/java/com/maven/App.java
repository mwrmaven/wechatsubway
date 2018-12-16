package com.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Hello world!
 */
@SpringBootApplication
public class App extends SpringBootServletInitializer {

    // TODO 类继承SpringBootServletInitializer，并重写configure方法和修改pom.xml文件，来使用外部的tomcat，如果不使用外部的tomcat，则不需要如此
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        System.out.println("程序启动!");
    }

}
