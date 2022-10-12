package com.ecjtu.ferry.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    private Integer fid;
    private Integer uid;
    private String context;
    private Date fdate;
}
