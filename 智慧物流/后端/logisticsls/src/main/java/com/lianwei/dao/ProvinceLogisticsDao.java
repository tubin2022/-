package com.lianwei.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProvinceLogisticsDao {
    public Integer findProvinceLogistics(@Param("time") String time,@Param("pro") String pro);
}
