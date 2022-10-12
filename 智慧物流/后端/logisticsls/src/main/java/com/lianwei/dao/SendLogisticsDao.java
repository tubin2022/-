package com.lianwei.dao;

import com.lianwei.domain.dto.SendLogisticsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SendLogisticsDao {
    public List<SendLogisticsDTO> findSendLogistics(@Param("pro") String pro,@Param("time") String time);
}
