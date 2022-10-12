package com.lianwei.dao;

import com.lianwei.domain.dto.ChinaCompanyDTO;
import com.lianwei.domain.entity.ChinaCompany;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChinaCompanyDao {
    /**
     * 通过时间查询全国物流各公司占比
     * @param time
     * @return
     */
    public List<ChinaCompanyDTO> findChinaCompany(String time);
}
