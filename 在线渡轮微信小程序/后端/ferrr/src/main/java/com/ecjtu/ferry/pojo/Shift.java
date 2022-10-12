package com.ecjtu.ferry.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shift {
    private Integer sid;
    private String startCity;
    private String endCity;
    private Date startTime;
    private Date endTime;
}
