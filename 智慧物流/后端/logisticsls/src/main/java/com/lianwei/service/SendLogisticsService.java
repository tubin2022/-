package com.lianwei.service;

import com.lianwei.domain.dto.SendLogisticsDTO;

import java.util.List;

public interface SendLogisticsService {
    public List<SendLogisticsDTO> findSendLogistics(String pro,String time);
}
