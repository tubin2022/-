package com.ecjtu.ferry.dao;

import com.ecjtu.ferry.pojo.Shift;
import com.ecjtu.ferry.pojo.dto.TicketsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatisticsDao {
    /**
     * 根据日期查询每天售票情况
     * 参数date为前端格式化的字符串日期参数，例如：2022-06-22
     * @param date
     * @return
     */
    public List<TicketsDTO> everyDaySales(String date);

    /**
     * 根据日期查询每个城市每天总共发了多少趟班次
     * 参数date为前端格式化的字符串日期参数，例如：2022-06-22
     * @param date
     * @return
     */
    public List<Shift> everyDayShift(String date);
}
