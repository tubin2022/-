package com.ecjtu.ferry.controller;

import com.ecjtu.ferry.common.ConditionNotException;
import com.ecjtu.ferry.pojo.vo.ResultVO;
import com.ecjtu.ferry.service.Impl.StatisticsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@Api(tags = "统计相关接口")
public class StatisticsController {
    @Autowired
    private StatisticsServiceImpl statisticsService;

    @GetMapping("/daySale")
    @ApiOperation("每天各路线的售票情况")
    @ApiImplicitParam(name = "date",value = "时间(年-月-日)",dataType = "String",required = true)
    public ResultVO everyDaySaled(String date) throws ConditionNotException {
        ResultVO resultVO = statisticsService.everyDaySales(date);
        return resultVO;
    }

    @GetMapping("/dayShift")
    @ApiOperation("每天每个城市发出班次情况")
    @ApiImplicitParam(name = "date",value = "时间(年-月-日)",dataType = "String",required = true)
    public ResultVO everyDayShift(String date) throws ConditionNotException {
        ResultVO resultVO = statisticsService.everyDayShifts(date);
        return resultVO;
    }
}
