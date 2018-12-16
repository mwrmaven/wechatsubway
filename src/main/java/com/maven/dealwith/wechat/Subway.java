package com.maven.dealwith.wechat;

import com.maven.exceptions.PromptMessage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 地铁相关服务
 */
@Service
public class Subway {

    private static final Logger logger = LoggerFactory.getLogger(Subway.class);

    @Autowired
    ParseStationHtml psh;

    /**
     * 查询地铁的首末时刻表
     * @param station_name
     * @return
     */
    public String stationTime(String station_name) {
        // 从配置文件获取bjsubway的url
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "conf.properties")));
        } catch (IOException e) {
            logger.error(PromptMessage.CONF_ERROR);
        }

        String url = properties.getProperty("BJ.Station.Url");
        // 获取网页信息
        String html = getHtml(url);
        if (html.contains("地铁公告")) {
            return PromptMessage.WEB_ERROR;
        } else if (StringUtils.isBlank(station_name) || !html.contains(station_name)) {
            return PromptMessage.NOT_EXIST_ERROR;
        } else if ("error".equals(html)) {
            return PromptMessage.PAGE_FAILED_OT_GET_ERROR;
        }

        // 解析数据
        return psh.parse(html, station_name);
    }

    /**
     * 获取地铁网页信息
     * @param url 地铁网站地址
     * @return
     */
    private String getHtml(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            return "error";
        }
    }
}
