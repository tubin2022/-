package com.lianwei.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LostLogisticsDao {
    public Integer findLostLogistics(@Param("pro") String pro, @Param("time") String time);
}
