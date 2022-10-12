package com.lianwei.service.impl;

import com.lianwei.dao.SendLogisticsDao;
import com.lianwei.domain.dto.SendLogisticsDTO;
import com.lianwei.service.SendLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendLogisticsServiceImpl implements SendLogisticsService {
    @Autowired
    private SendLogisticsDao sendLogisticsDao;

    @Override
    public List<SendLogisticsDTO> findSendLogistics(String pro, String time) {
        return sendLogisticsDao.findSendLogistics(pro, time);
    }
}
