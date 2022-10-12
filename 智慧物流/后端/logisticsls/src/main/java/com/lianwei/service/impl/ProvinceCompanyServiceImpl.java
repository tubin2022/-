package com.lianwei.service.impl;

import com.lianwei.dao.ProvinceCompanyDao;
import com.lianwei.domain.dto.ProvinceCompanyDTO;
import com.lianwei.service.ProvinceCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceCompanyServiceImpl implements ProvinceCompanyService {
    @Autowired
    private ProvinceCompanyDao provinceCompanyDao;

    @Override
    public List<ProvinceCompanyDTO> findProvinceCompany(String time, String pro) {
        return provinceCompanyDao.findProvinceCompany(time,pro);
    }
}
