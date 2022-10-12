package com.ecjtu.ferry.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户订单
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer oid;
    private Integer uid;//用户id
    private Integer tid;//票务id
    private Float price;//票务价格
    private String startCity;//出发城市
    private String endCity;//到达城市
    private Date startTime;//出发时间
    private Date endTime;//到达时间
}
