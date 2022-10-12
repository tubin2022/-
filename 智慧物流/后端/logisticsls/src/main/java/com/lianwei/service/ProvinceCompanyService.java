package com.lianwei.service;

import com.lianwei.domain.dto.ProvinceCompanyDTO;

import java.util.List;

public interface ProvinceCompanyService {
    /**
     * 通过省份，时间查询各省物流各公司占比数据
     * @param time
     * @param pro
     * @return
     */
    public List<ProvinceCompanyDTO> findProvinceCompany(String time,String pro);
}
