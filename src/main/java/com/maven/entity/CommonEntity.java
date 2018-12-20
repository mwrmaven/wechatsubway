package com.maven.entity;

import java.io.Serializable;

public class CommonEntity implements Serializable {

    private static final long serialVersionUID = -2226016185995268781L;

    private int code;

    private String message;

    private Object data;

    public CommonEntity(int code) {
        this.code = code;
    }

    public CommonEntity(Object data) {
        this.code = 200;
        this.data = data;
    }

    public CommonEntity(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
