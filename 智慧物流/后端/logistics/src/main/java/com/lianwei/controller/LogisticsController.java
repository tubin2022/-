package com.lianwei.controller;

import com.lianwei.domain.entity.Logistics;
import com.lianwei.domain.vo.ResultVO;
import com.lianwei.domain.vo.SelectLogisticsVO;
import com.lianwei.domain.vo.UpdateLogisticsVO;
import com.lianwei.service.LogisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 物流controller
 */
@RestController
@RequestMapping("/logistics")
@Api(tags = "用户物流管理接口")
public class LogisticsController {
    @Autowired
    private LogisticsService logisticsService;

    /**
     * 用户寄件
     * @param logistics 寄件的物流信息
     * @return
     */
    @PostMapping("/send")
    @ApiOperation("用户寄件")
    public ResultVO send(@RequestBody Logistics logistics){
        logisticsService.send(logistics);
        ResultVO resultVO = new ResultVO();
        return resultVO;
    }

    /**
     * 用户派件
     * @param logisticsId 物流id
     * @param dispatchUserId 派件人id
     * @param arriveAddress  到达地址
     * @return
     */
    @PostMapping("/dispatch")
    @ApiOperation("用户派件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "logisticsId",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "dispatchUserId",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "arriveAddress",paramType = "query",dataType = "String",required = true)
    })
    public ResultVO dispatch(Integer logisticsId, Integer dispatchUserId, @RequestParam("arriveAddress") List<BigDecimal> arriveAddress){
        UpdateLogisticsVO updateLogisticsVO = new UpdateLogisticsVO();
        updateLogisticsVO.setLogisticsId(logisticsId);
        updateLogisticsVO.setDispatchUserId(dispatchUserId);
        updateLogisticsVO.setPassAddress(arriveAddress);
        logisticsService.dispatch(updateLogisticsVO);
        ResultVO resultVO = new ResultVO();
        return resultVO;
    }

    /**
     * 用户签收
     * @param logisticsId
     * @param receiveUserPhone
     * @return
     */
    @PostMapping("/receive")
    @ApiOperation("用户签收")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "logisticsId",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "receiveUserName",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "receiveUserPhone",paramType = "query",dataType = "String",required = true)
    })
    public ResultVO receive(Integer logisticsId,String receiveUserName,String receiveUserPhone){
        ResultVO resultVO = logisticsService.receive(logisticsId,receiveUserName, receiveUserPhone);
        return resultVO;
    }

    /**
     * 用户签收
     * @param logisticsId
     * @param receiveUserPhone
     * @return
     */
    @PostMapping("/back")
    @ApiOperation("用户退货")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "logisticsId",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "receiveUserName",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "receiveUserPhone",paramType = "query",dataType = "String",required = true)
    })
    public ResultVO back(Integer logisticsId,String receiveUserName,String receiveUserPhone){
        ResultVO resultVO = logisticsService.back(logisticsId,receiveUserName, receiveUserPhone);
        return resultVO;
    }

    @GetMapping("/pageQuery")
    @ApiOperation("分页查询物流信息")
    public ResultVO pageQuery(@ModelAttribute SelectLogisticsVO selectLogisticsVO){
        ResultVO resultVO = logisticsService.pageQueryLogistics(selectLogisticsVO);
        return resultVO;
    }
}
