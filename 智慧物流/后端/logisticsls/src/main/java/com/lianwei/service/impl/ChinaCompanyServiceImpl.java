package com.lianwei.service.impl;

import com.lianwei.dao.ChinaCompanyDao;
import com.lianwei.domain.dto.ChinaCompanyDTO;
import com.lianwei.service.ChinaCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 全国物流各公司占比service实现类
 */
@Service
public class ChinaCompanyServiceImpl implements ChinaCompanyService {
    @Autowired
    private ChinaCompanyDao chinaCompanyDao;

    @Override
    public List<ChinaCompanyDTO> findChinaCompany(String time) {
        return chinaCompanyDao.findChinaCompany(time);
    }
}
