package com.ecjtu.ferry.common;

/**
 * 不能退票异常（过了售卖时间）,抛出后由全局异常捕获后返回错误信息
 */
public class CanNotReturnTicketException extends Exception{
    public CanNotReturnTicketException() {
        super();
    }

    public CanNotReturnTicketException(String message) {
        super(message);
    }
}
