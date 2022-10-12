package com.lianwei.dao;

import com.lianwei.domain.dto.TransactionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TransactionDao {
    public List<TransactionDTO> findSend(@Param("pro") String pro, @Param("time") String time);
    public List<TransactionDTO> findDispatch(@Param("pro") String pro, @Param("time") String time);
}
