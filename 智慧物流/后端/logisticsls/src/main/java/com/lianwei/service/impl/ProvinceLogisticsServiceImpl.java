package com.lianwei.service.impl;

import com.lianwei.dao.ProvinceLogisticsDao;
import com.lianwei.service.ProvinceLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinceLogisticsServiceImpl implements ProvinceLogisticsService {
    @Autowired
    private ProvinceLogisticsDao provinceLogisticsDao;

    @Override
    public Integer findProvinceLogistics(String time, String pro) {
        return provinceLogisticsDao.findProvinceLogistics(time, pro);
    }
}
