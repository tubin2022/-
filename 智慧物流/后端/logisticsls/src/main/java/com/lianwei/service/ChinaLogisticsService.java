package com.lianwei.service;

public interface ChinaLogisticsService {
    /**
     * 通过时间查询全国物流总量
     * @param time
     * @return
     */
    public Integer findChinaLogistics(String time);
}
