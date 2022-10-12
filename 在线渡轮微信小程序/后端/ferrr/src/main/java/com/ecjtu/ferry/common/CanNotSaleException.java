package com.ecjtu.ferry.common;

/**
 * 不能售卖异常（没有在售卖时间范围内）,抛出后由全局异常捕获后返回错误信息
 */
public class CanNotSaleException extends Exception{
    public CanNotSaleException() {
        super();
    }

    public CanNotSaleException(String message) {
        super(message);
    }
}
