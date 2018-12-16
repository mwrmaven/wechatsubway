package com.maven.utils;

import com.maven.entity.TextMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析和格式化xml的数据
 * create by maven 2018/12/14
 */
public class XmlUtil {

    /**
     * 解析xml格式的数据的输入流
     * @param inputStream
     * @return
     */
    public static Map<String, String> parseXml(InputStream inputStream) {
        // 解析xml的工具，例如jsoup是解析页面信息的工具类
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        Map<String, String> requestParam = new HashMap<>();
        // 获取根元素
        Element root = document.getRootElement();
        // 获取根元素上的所有子节点
        List<Element> elements = root.elements();
        for (Element element : elements) {
            requestParam.put(element.getName(), element.getText());
            System.out.println("标签为：" + element.getName() + "; 内容为：" + element.getText());
        }

        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return requestParam;
    }

    /**
     * 格式化结果数据，以便使用xml格式输出
     * @param textMessage
     * @return
     */
    public static String messageToXml(TextMessage textMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<ToUserName><![CDATA[");
        sb.append(textMessage.getToUserName());
        sb.append("]]></ToUserName>");
        sb.append("<FromUserName><![CDATA[");
        sb.append(textMessage.getFromUserName());
        sb.append("]]></FromUserName>");
        sb.append("<CreateTime>");
        sb.append(textMessage.getCreateTime());
        sb.append("</CreateTime>");
        sb.append("<MsgType><![CDATA[text]]></MsgType>");
        sb.append("<Content><![CDATA[");
        sb.append(textMessage.getContent());
        sb.append("]]><Content>");
        sb.append("</xml>");

        return sb.toString();
    }
}
