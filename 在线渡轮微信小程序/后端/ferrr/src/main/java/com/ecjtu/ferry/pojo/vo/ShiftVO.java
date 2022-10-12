package com.ecjtu.ferry.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftVO {
    private Integer pageNo;
    private Integer pageSize;
    private Integer sid;
    private String startCity;
    private String endCity;
    private String startTime;
    private String endTime;
}
