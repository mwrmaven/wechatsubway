package com.maven.entity;

import java.io.Serializable;

/**
 * 微信消息基础类
 * create by maven 2018/12/14
 */
public class BaseMessage implements Serializable {

    private static final long serialVersionUID = -6971210965290496497L;

    // 接收方账号
    private String toUserName;
    // 发送方微信号
    private String fromUserName;
    // 消息创建时间
    private long createTime;
    // 消息类型（text/music/news）
    private String msgType;
    // 位0x0001被标记时，星标刚收到的消息
    private int funcFlag;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public int getFuncFlag() {
        return funcFlag;
    }

    public void setFuncFlag(int funcFlag) {
        this.funcFlag = funcFlag;
    }
}
