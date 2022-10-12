package com.lianwei.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DispatchLogistics implements Serializable {
    private Integer id;
    private String time;
    private String pro;
    private String city;
    private Integer num;
}
