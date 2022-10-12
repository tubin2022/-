package com.ecjtu.ferry.common;

/**
 * 不能删除班次异常
 * 在班次已经发出，若管理员想删除该发出的班次会抛出该异常
 */
public class CanNotDeleteShiftException extends Exception{
    public CanNotDeleteShiftException() {
        super();
    }

    public CanNotDeleteShiftException(String message) {
        super(message);
    }
}
