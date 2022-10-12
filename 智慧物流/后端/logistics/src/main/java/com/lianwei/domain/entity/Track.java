package com.lianwei.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Track implements Serializable {
    private Integer logisticsId;
    private String sendProvince;
    private String sendCity;
    private String receiveProvince;
    private String receiveCity;
    private String sendTime;
    private String startProvince;
    private String startCity;
    private String dispatchedProvince;
    private String dispatchedCity;
    private Long dispatchedTime;
    private Integer dispatchedLost;
    private Long signTime;
    private Long backTime;
    private String company;
    private Integer logisticsStatus;
}
