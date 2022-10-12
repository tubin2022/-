package com.lianwei.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DispatchLogisticsDTO implements Serializable {
    private String pro;
    private String city;
    private Integer sums;
}
