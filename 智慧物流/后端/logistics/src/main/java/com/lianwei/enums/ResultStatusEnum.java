package com.lianwei.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 这里是列举系统中可能出现的一些操作码，和操作返回信息
 */
@AllArgsConstructor
@Getter
public enum ResultStatusEnum {
    SUCCESS(1000,"操作成功"),
    //用户操作系列结果
    USER_NOT_LOGIN(2000,"用户未登录"),
    USER_NOT_USERACCOUNT(2001,"用户名不存在"),
    USER_ERROR_PASSWORD(2002,"用户密码错误"),
    USER_LOGIN_SUCCESS(2003,"登录成功"),
    ERROR_OLDPASSWORD(2004,"原密码错误"),
    //物流操作系列结果
    LOGISTICS_ERROR_RECEIVE_USER(3000,"签收人信息错误");
    private Integer code;
    private String msg;
}
