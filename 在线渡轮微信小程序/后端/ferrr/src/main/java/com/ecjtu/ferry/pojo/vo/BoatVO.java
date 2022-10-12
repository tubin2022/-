package com.ecjtu.ferry.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoatVO {
    private Integer pageNo;
    private Integer pageSize;
    private Integer bid;
}
