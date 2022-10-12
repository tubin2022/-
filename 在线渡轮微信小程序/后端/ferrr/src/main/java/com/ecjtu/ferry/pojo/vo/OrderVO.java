package com.ecjtu.ferry.pojo.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO {
    private Integer pageNo;
    private Integer pageSize;
    private Integer oid;
    private Integer uid;//用户id
    private Integer tid;//票务id
    private String startCity;//出发城市
    private String endCity;//到达城市
    private String status;//状态
}
