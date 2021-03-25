package com.maven.controller;

import com.maven.dealwith.wechat.Subway;
import com.maven.entity.CommonEntity;
import com.maven.entity.TextMessage;
import com.maven.utils.EncryptionUtil;
import com.maven.utils.IpUtil;
import com.maven.utils.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

/**
 * 接收和处理微信发送过来的数据，并返回结果
 * @author mavenr
 * create by maven on 2018/12/14
 */
@Slf4j
@RestController
public class WechatSubway {

    /**
     * token密钥，用于验证微信消息是否是合法的
     */
    private final String TOKEN = "stationQuery";

    @Autowired
    private Subway subway;

    /**
     * 验证微信请求是否合法
     * @param request
     * @param response
     */
    @GetMapping(path = "/chat", produces = "application/json")
    public void getWx(HttpServletRequest request, HttpServletResponse response) {
        log.info("本次请求的发起的ip地址为：{}", IpUtil.parseIpAddr(request));

        log.info("自定义的token值为：{}", request.getParameter("station"));

        // 微信加密签名
        String signature = request.getParameter("signature");
        // 随机字符串
        String randomStr = request.getParameter("echostr");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String randomNum = request.getParameter("nonce");

        log.info("signature == " + signature);
        log.info("echostr == " + randomStr);
        log.info("timestamp == " + timestamp);
        log.info("nonce == " + randomNum);

        String[] str = {TOKEN, timestamp, randomNum};
        Arrays.sort(str);
        String bigStr = str[0] + str[1] + str[2];

        // SHA-1加密
        String digest = EncryptionUtil.sha(bigStr);

        // 验证签名是否合法，合法，则返回随机字符串
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            if (digest.equalsIgnoreCase(signature)) {
                printWriter.print(randomStr);
            } else {
                printWriter.print(randomStr + "-false");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    /**
     * 处理微信请求
     * @param request
     * @param response
     */
    @PostMapping(path = "/chat", produces = "application/json")
    public void postWx(HttpServletRequest request, HttpServletResponse response) {
        // 设置response的返回格式，避免出现乱码
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            // 接受微信传来的消息，并返回消息
            String result = acceptMessage(request);
            log.info("请求到的结果为：{}", request);
            pw.print(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    @PostMapping(path = "/beginend", produces = "application/json")
    public CommonEntity getBeginAndEnd(@RequestBody String stationName) {
        String result = subway.stationTime(stationName);
        return CommonEntity.successReturn(result);
    }

    /**
     * 微信请求的实际处理
     * @param request
     * @return
     */
    private String acceptMessage(HttpServletRequest request) {
        // 存储微信xml请求中的参数
        Map<String, String> requestParam = null;
        try {
            requestParam = XmlUtil.parseXml(request.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 发送方账号，即用户账号，当公众号回复消息时，该账号就是接受方的账号
        String fromUserName = requestParam.get("FromUserName");
        // 接收方账号，即微信公众号开发者账号
        String toUserName = requestParam.get("ToUserName");
        // 消息类型
        String msgType = requestParam.get("MsgType");
        // 消息内容
        String content = "";
        if ("text".equals(msgType)) {
            content = requestParam.get("Content");
        } else if ("event".equals(msgType)) {
            content = requestParam.get("Event");
        }

        // 回复文本消息
        TextMessage tm = new TextMessage();
        tm.setToUserName(fromUserName);
        tm.setFromUserName(toUserName);
        tm.setCreateTime(System.currentTimeMillis());
        tm.setMsgType("text");

        if ("subscribe".equals(content)) {
            tm.setContent("欢迎关注小哥的公众号，现有地铁首末时刻表查询功能，微信发送地铁站名称即可收到，例如发送\"知春路\"，更多功能正在开发中，敬请关注～");
        } else if ("unsubscribe".equals(content)) {
            tm.setContent("");
        } else {
            tm.setContent(subway.stationTime(content));
        }

        // 将回复的消息格式化为xml格式
        String result = XmlUtil.messageToXml(tm);
        log.info("查询地铁站回复的消息为：{}", result);

        return result;
    }
}
