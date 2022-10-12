package com.lianwei.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface StatusLogisticsService {
    public List<List> findStatusLogistics(String pro,String time) throws ParseException;
}
