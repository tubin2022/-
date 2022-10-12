package com.ecjtu.ferry.common;

/**
 * 自定义手机号验证异常
 */
public class VerifyPhoneFailedException extends Exception{
    public VerifyPhoneFailedException() {
        super();
    }

    public VerifyPhoneFailedException(String message) {
        super(message);
    }
}
