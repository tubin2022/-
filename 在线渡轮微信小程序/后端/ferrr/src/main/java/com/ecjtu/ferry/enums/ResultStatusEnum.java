package com.ecjtu.ferry.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回结果
 */
@Getter
@AllArgsConstructor
public enum ResultStatusEnum {
    //1000系列  通用错误
    SUCCESS(1000,"操作成功"),
    FAILED(1001,"接口错误"),
    VALIDATE_FAILED(1002,"参数校验失败"),
    ERROR(1003,"未知错误"),
    FILE_SIZE_OVERFLOW(1004,"上传文件字节被限制"),
    CONDITION_IS_NOT(1005,"操作条件不能为空"),
    INPUT_ERROR(1006,"输入条件格式错误"),

    //2000系列 用户错误
    USER_NOT_EXIST(2000,"用户不存在"),
    USER_LOGIN_FAIL(2001,"用户名或密码错误"),
    USER_NOT_LOGIN(2002,"用户还未登录，请先登录"),
    NO_PERMISSION(2003,"权限不足，请联系管理员"),
    LOGOUT_SUCCESS(2004,"退出登录成功，请重新登录"),
    LOGIN_EXPIRATION(2005,"token过期,请重新登录"),
    AUDIT_FAILED(3000,"审核失败"),

    //3000系列,票务错误
    EXIST_BOAT_EXCUTE_SHIFT(3001,"存在该渡轮执行该班次的票务"),
    CAN_NOT_SALE_TICKET(3002,"该票务暂不进行售卖"),
    CAN_NOT_RETURN_TICKET(3003,"当前时间不允许退票"),
    CAN_NOT_DELETE_SHIFT(3004,"该班次已发出，拒绝删除"),

    //4000系列，数据验证错误
    VERIFY_PHONE_FAILED(4000,"手机格式错误"),
    VERIFY_EMAIL_FAILED(4001,"邮箱格式错误");
    private final Integer code;
    private final String msg;
}
