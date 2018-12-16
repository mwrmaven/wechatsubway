package com.maven.entity;

/**
 * 微信消息实体类
 * create by maven 2018/12/14
 */
public class TextMessage extends BaseMessage implements Cloneable  {
    // 回复的消息内容
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TextMessage clone() {
        try {
            return (TextMessage) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
