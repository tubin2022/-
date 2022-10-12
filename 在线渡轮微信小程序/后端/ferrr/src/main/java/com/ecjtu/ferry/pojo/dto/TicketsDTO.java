package com.ecjtu.ferry.pojo.dto;


import com.ecjtu.ferry.pojo.Boat;
import com.ecjtu.ferry.pojo.Shift;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketsDTO {
    private Integer tid;
    private Integer bid;//渡轮编号
    private Integer sid;//班次编号
    private Integer total;//总票数
    private Integer surplus;//剩余票数
    private Float price;//价格
    private Date startSale;//起售时间
    private Date endSale;//停售时间

    private Boat boat;//对应票务的渡轮信息
    private Shift shift;//对应票务的班次信息
}
