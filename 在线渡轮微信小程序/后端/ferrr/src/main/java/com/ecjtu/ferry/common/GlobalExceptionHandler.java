package com.ecjtu.ferry.common;

import com.ecjtu.ferry.enums.ResultStatusEnum;
import com.ecjtu.ferry.pojo.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.ParseException;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理在需要条件参数而用户没有带参数的请求异常，并响应错误信息
     * @param e
     * @return
     */
    @ExceptionHandler(value = ConditionNotException.class)//指定拦截异常  处理所有的异常信息
    public ResultVO handleAllException(ConditionNotException e){
//        e.printStackTrace();
        return  new ResultVO(ResultStatusEnum.CONDITION_IS_NOT);
    }

    /**
     * 处理用户输入的参数条件格式错误导致无法解析的异常，并响应错误信息
     * @param e
     * @return
     */
    @ExceptionHandler(value = ParseException.class)//指定拦截异常  处理所有的异常信息
    public ResultVO handleAllException(ParseException e){
//        e.printStackTrace();
        return  new ResultVO(ResultStatusEnum.INPUT_ERROR);
    }

    /**
     * 处理系统内部不允许出现重复的异常，并响应错误信息
     * @param e
     * @return
     */
    @ExceptionHandler(value = RepeatException.class)//指定拦截异常  处理所有的异常信息
    public ResultVO handleAllException(RepeatException e){
//        e.printStackTrace();
        return  new ResultVO(ResultStatusEnum.EXIST_BOAT_EXCUTE_SHIFT);
    }

    /**
     * 处理票务暂不允许售卖的异常，并响应暂不售卖的信息
     * @param e
     * @return
     */
    @ExceptionHandler(value = CanNotSaleException.class)//指定拦截异常  处理所有的异常信息
    public ResultVO handleAllException(CanNotSaleException e){
//        e.printStackTrace();
        return  new ResultVO(ResultStatusEnum.CAN_NOT_SALE_TICKET);
    }

    /**
     * 处理票务暂不允许退票的异常，并响应拒绝退票的信息
     * @param e
     * @return
     */
    @ExceptionHandler(value = CanNotReturnTicketException.class)//指定拦截异常  处理所有的异常信息
    public ResultVO handleAllException(CanNotReturnTicketException e){
//        e.printStackTrace();
        return  new ResultVO(ResultStatusEnum.CAN_NOT_RETURN_TICKET);
    }

    /**
     * 处理手机号码格式验证错误异常，并响应验证错误信息
     * @param e
     * @return
     */
    @ExceptionHandler(value = VerifyPhoneFailedException.class)//指定拦截异常  处理所有的异常信息
    public ResultVO handleAllException(VerifyPhoneFailedException e){
//        e.printStackTrace();
        return  new ResultVO(ResultStatusEnum.VERIFY_PHONE_FAILED);
    }

    /**
     * 处理邮箱格式验证错误异常，并响应验证错误信息
     * @param e
     * @return
     */
    @ExceptionHandler(value = VerifyEmailFailedException.class)//指定拦截异常  处理所有的异常信息
    public ResultVO handleAllException(VerifyEmailFailedException e){
//        e.printStackTrace();
        return  new ResultVO(ResultStatusEnum.VERIFY_EMAIL_FAILED);
    }

    /**
     * 处理拒绝删除已发出班次的异常，并响应错误信息
     * @param e
     * @return
     */
    @ExceptionHandler(value = CanNotDeleteShiftException.class)//指定拦截异常  处理所有的异常信息
    public ResultVO handleAllException(CanNotDeleteShiftException e){
//        e.printStackTrace();
        ResultVO resultVO = new ResultVO(ResultStatusEnum.CAN_NOT_DELETE_SHIFT);
        resultVO.setMsg(e.getMessage());
        return  resultVO;
    }

    /**
     * 处理其它异常，并响应错误信息
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)//指定拦截异常  处理所有的异常信息
    public ResultVO handleAllException(Exception e){
//        e.printStackTrace();
        return  new ResultVO(ResultStatusEnum.ERROR);
    }
}
