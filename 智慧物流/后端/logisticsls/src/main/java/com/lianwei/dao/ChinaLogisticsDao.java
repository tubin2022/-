package com.lianwei.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChinaLogisticsDao {
    public Integer findChinaLogistics(String time);
}
