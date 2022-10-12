package com.ecjtu.ferry.pojo.dto;

import com.ecjtu.ferry.pojo.Boat;
import com.ecjtu.ferry.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer oid;
    private Integer uid;//用户id
    private Integer tid;//票务id
    private Float price;//票务价格
    private String startCity;//出发城市
    private String endCity;//到达城市
    private Date startTime;//出发时间
    private Date endTime;//到达时间

    private User user;//对应订单的用户信息

    private Boat boat;//对应订单船票渡轮的信息
}
