package com.maven.conf;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 项目启动的同时加载配置信息
 * create by mavenr 2018/12/19
 */
@Component
@Order(1)
public class Conf implements ApplicationRunner {

    private static final Properties properties = new Properties();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        File file;
        // 根据系统的参数不同，读取不同的配置文件
        if ("master".equalsIgnoreCase(System.getenv("WECHAT"))) {
            file = ResourceUtils.getFile("");
        } else {
            file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "conf.properties");
        }
        System.out.println("配置文件路径：" + file.getPath());
        InputStream is = new FileInputStream(file);
        properties.load(is);
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
