package com.ecjtu.ferry.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackVO {
    private Integer pageNo;
    private  Integer pageSize;
    private Integer uid;
    private String context;
    private String fdate;
}
