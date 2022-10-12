package com.lianwei.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceCompany implements Serializable {
    private Integer id;
    private String time;
    private String pro;
    private String com;
    private Integer num;
}
