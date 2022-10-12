package com.lianwei.common;

import com.lianwei.domain.vo.ResultVO;
import com.lianwei.enums.ResultStatusEnum;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 用户名错误处理
     * @return
     */
    @ExceptionHandler(UnknownAccountException.class)
    public ResultVO unknowAccountExceptionHandler(){
        ResultVO resultVO = new ResultVO(ResultStatusEnum.USER_NOT_USERACCOUNT);
        return resultVO;
    }

    /**
     * 用户密码错误处理
     * @return
     */
    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResultVO incorrectCredentialsExceptionHandler(){
        ResultVO resultVO = new ResultVO(ResultStatusEnum.USER_ERROR_PASSWORD);
        return resultVO;
    }
}
