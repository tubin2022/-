package com.lianwei.service.impl;

import com.lianwei.dao.ChinaLogisticsDao;
import com.lianwei.service.ChinaLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChinaLogisticsServiceImpl implements ChinaLogisticsService {
    @Autowired
    private ChinaLogisticsDao chinaLogisticsDao;
    /**
     * 通过时间查询全国物流总量
     * @param time
     * @return
     */
    @Override
    public Integer findChinaLogistics(String time) {
        return chinaLogisticsDao.findChinaLogistics(time);
    }
}
