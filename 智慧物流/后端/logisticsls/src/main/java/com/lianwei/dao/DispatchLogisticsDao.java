package com.lianwei.dao;

import com.lianwei.domain.dto.DispatchLogisticsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DispatchLogisticsDao {
    public List<DispatchLogisticsDTO> findDispatchLogistics(@Param("pro") String pro, @Param("time") String time);
}
