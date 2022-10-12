package com.ecjtu.ferry.common;

/**
 * 操作条件为空异常（在需要参数的接口中没有携带参数）,抛出后由全局异常捕获后返回错误信息
 */
public class ConditionNotException extends Exception{
    public ConditionNotException() {
        super();
    }

    public ConditionNotException(String message) {
        super(message);
    }
}
