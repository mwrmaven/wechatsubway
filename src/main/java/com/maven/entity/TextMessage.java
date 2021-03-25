package com.maven.entity;

import lombok.*;

/**
 * 微信消息实体类
 * @author mavenr
 * create by maven 2018/12/14
 */
@Data
@ToString
public class TextMessage extends BaseMessage implements Cloneable  {
    /**
     * 回复的消息内容
     */
    private String content;

    @Override
    public TextMessage clone() {
        try {
            return (TextMessage) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
