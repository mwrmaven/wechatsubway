package com.maven.sysenum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mavenr
 * @Classname CodeEnum
 * @Description 返回状态码
 * @Date 2021/3/25 15:43
 */
@Getter
@AllArgsConstructor
public enum CodeEnum {
    /**
     * 地铁查找失败
     */
    FINDFAIL(5204, "地铁查找失败"),
    /**
     * 地铁查找数据为空
     */
    FINDEMPTY(5203, "地铁查找数据为空"),
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 错误
     */
    ERROR(500, "错误");


    private int code;

    private String desc;

}
