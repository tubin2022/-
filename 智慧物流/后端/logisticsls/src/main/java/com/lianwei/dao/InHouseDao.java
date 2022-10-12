package com.lianwei.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InHouseDao {
    public Integer findInHouse(@Param("pro") String pro, @Param("time") String time);
}
