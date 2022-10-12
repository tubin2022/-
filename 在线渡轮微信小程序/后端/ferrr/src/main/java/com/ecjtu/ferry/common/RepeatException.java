package com.ecjtu.ferry.common;

/**
 * 重复异常（一辆轮船不允许在同一时间执行多趟班次）,抛出后由全局异常处理捕获返回错误信息
 */
public class RepeatException extends Exception{
    public RepeatException() {
        super();
    }

    public RepeatException(String message) {
        super(message);
    }
}
