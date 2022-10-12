package com.lianwei.dao;

import com.lianwei.domain.dto.ProvinceCompanyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProvinceCompanyDao {
    /**
     * 通过时间，省份查询各省份物流各公司占比数据
     * @param time
     * @param pro
     * @return
     */
    public List<ProvinceCompanyDTO> findProvinceCompany(@Param("time") String time, @Param("pro") String pro);
}
