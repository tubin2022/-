package com.ecjtu.ferry.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Boat {
    private Integer bid;
    private String bname;
    private Date produceDate;
    private String bphoto;
    private Integer maxPeople;
    private String profile;
}
