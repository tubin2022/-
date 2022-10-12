package com.lianwei.domain.vo;

import com.lianwei.enums.ResultStatusEnum;
import lombok.Data;

@Data
public class ResultVO<T> {
    private Integer code;
    private String msg;
    private T data;

    /**
     * 更新操作成功
     */
    public ResultVO(){
        this.code= ResultStatusEnum.SUCCESS.getCode();
        this.msg= ResultStatusEnum.SUCCESS.getMsg();
    }


    /**
     * 查询操作成功
     */
    public ResultVO(T data){
        if(data==null){
            this.code= ResultStatusEnum.SUCCESS.getCode();
            this.msg= ResultStatusEnum.SUCCESS.getMsg();
        }else{
            this.code= ResultStatusEnum.SUCCESS.getCode();
            this.msg= ResultStatusEnum.SUCCESS.getMsg();
            this.data=data;
        }
    }

    /**
     * 发生异常
     */
    public ResultVO(ResultStatusEnum resultStatusEnum){
        this.code= resultStatusEnum.getCode();
        this.msg= resultStatusEnum.getMsg();
    }

    /**
     * 数据校验错误
     */
    public ResultVO(ResultStatusEnum resultStatusEnum, T data){
        this.code= resultStatusEnum.getCode();
        this.msg= resultStatusEnum.getMsg();
        this.data=data;
    }
}
