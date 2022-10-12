package com.lianwei.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 这里是列举系统中可能出现的一些操作码，和操作返回信息
 */
@AllArgsConstructor
@Getter
public enum ResultStatusEnum {
    SUCCESS(1000,"操作成功");
    private Integer code;
    private String msg;
}
