package com.lianwei.service.impl;

import com.lianwei.dao.DispatchLogisticsDao;
import com.lianwei.domain.dto.DispatchLogisticsDTO;
import com.lianwei.service.DispatchLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DispatchLogisticsServiceImpl implements DispatchLogisticsService {
    @Autowired
    private DispatchLogisticsDao dispatchLogisticsDao;

    @Override
    public List<DispatchLogisticsDTO> findDispatchLogistics(String pro, String time) {
        return dispatchLogisticsDao.findDispatchLogistics(pro, time);
    }
}
