package com.ecjtu.ferry.pojo.vo;

import com.alibaba.fastjson.JSON;
import com.ecjtu.ferry.enums.ResultStatusEnum;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 返回给前端的实体类，封装了操作码、操作信息、以及操作返回的数据
 * @param <T>
 */
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

    /**
     * 输出json 响应信息
     * @param response
     * @throws IOException
     */
    public void out(HttpServletResponse response)throws IOException {
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(this));
    }
}
