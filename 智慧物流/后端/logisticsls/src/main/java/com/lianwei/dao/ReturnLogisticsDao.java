package com.lianwei.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReturnLogisticsDao {
    public Integer findReturnLogistics(@Param("pro") String pro, @Param("time") String time);
}
