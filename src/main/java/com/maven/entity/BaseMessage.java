package com.maven.entity;

import lombok.*;

import java.io.Serializable;

/**
 * 微信消息基础类
 * @author mavenr
 * create by maven 2018/12/14
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BaseMessage implements Serializable {
    /**
     * 接收方账号
     */
    private String toUserName;
    /**
     * 发送方微信号
     */
    private String fromUserName;
    /**
     * 消息创建时间
     */
    private Long createTime;
    /**
     * 消息类型（text/music/news）
     */
    private String msgType;
    /**
     * 位0x0001被标记时，星标刚收到的消息
     */
    private int funcFlag;
}
