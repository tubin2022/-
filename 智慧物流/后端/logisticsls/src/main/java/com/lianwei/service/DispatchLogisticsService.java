package com.lianwei.service;

import com.lianwei.domain.dto.DispatchLogisticsDTO;

import java.util.List;

public interface DispatchLogisticsService {
    public List<DispatchLogisticsDTO> findDispatchLogistics(String pro,String time);
}
