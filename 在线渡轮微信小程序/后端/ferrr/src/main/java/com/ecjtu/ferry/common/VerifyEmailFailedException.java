package com.ecjtu.ferry.common;

/**
 * 自定义邮箱格式验证异常
 */
public class VerifyEmailFailedException extends Exception{
    public VerifyEmailFailedException() {
        super();
    }

    public VerifyEmailFailedException(String message) {
        super(message);
    }
}
