package com.lianwei.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InHouse implements Serializable {
    private Integer id;
    private String time;
    private String pro;
    private Integer num;
}
