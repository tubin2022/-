package com.ecjtu.ferry.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tickets {
    private Integer tid;
    private Integer bid;//渡轮编号
    private Integer sid;//班次编号
    private Integer total;//总票数
    private Integer surplus;//剩余票数
    private Float price;//价格
    private Date startSale;//起售时间
    private Date endSale;//停售时间
}
