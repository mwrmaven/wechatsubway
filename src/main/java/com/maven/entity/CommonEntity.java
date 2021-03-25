package com.maven.entity;

import com.maven.sysenum.CodeEnum;
import lombok.*;

import java.io.Serializable;

/**
 * @Classname CommonEntity
 * @Description 返回信息基础类
 * @Date 2021/3/25 15:36
 * @author mavenr
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommonEntity implements Serializable {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回的对象信息
     */
    private Object data;

    public static CommonEntity successReturn(Object data) {
        return new CommonEntity(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getDesc(), data);
    }

    public static CommonEntity errorReturn(int code, Object data) {
        return new CommonEntity(code, CodeEnum.ERROR.getDesc(), data);
    }
}
