package com.lianwei.service;

import com.lianwei.domain.dto.ChinaCompanyDTO;

import java.util.List;

public interface ChinaCompanyService {
    /**
     * 通过时间查询全国物流各公司占比
     * @param time
     * @return
     */
    public List<ChinaCompanyDTO> findChinaCompany(String time);
}
