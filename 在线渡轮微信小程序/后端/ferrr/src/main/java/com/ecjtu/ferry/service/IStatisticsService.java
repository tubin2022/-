package com.ecjtu.ferry.service;


import com.ecjtu.ferry.common.ConditionNotException;
import com.ecjtu.ferry.pojo.vo.ResultVO;


public interface IStatisticsService {
    /**
     * 根据日期查询每天售票情况
     * 参数date为前端格式化的字符串日期参数，例如：2022-06-22
     * @param date
     * @return
     */
    public ResultVO everyDaySales(String date) throws ConditionNotException;

    /**
     * 根据日期查询每个城市每天总共发了多少趟班次
     * 参数date为前端格式化的字符串日期参数，例如：2022-06-22
     * @param date
     * @return
     */
    public ResultVO everyDayShifts(String date) throws ConditionNotException;
}
