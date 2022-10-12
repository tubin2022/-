package com.lianwei.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceCompanyDTO implements Serializable {
    private String name;
    private Integer value;
}
