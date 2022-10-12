package com.ecjtu.ferry.controller;

import com.ecjtu.ferry.common.ConditionNotException;
import com.ecjtu.ferry.enums.ResultStatusEnum;
import com.ecjtu.ferry.pojo.Boat;
import com.ecjtu.ferry.pojo.vo.BoatVO;
import com.ecjtu.ferry.pojo.vo.ResultVO;
import com.ecjtu.ferry.service.Impl.BoatServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/boat")
@Api(tags = "渡轮信息管理相关接口")
public class BoatController {
    @Autowired
    private BoatServiceImpl iBoatService;


    @PostMapping("/addBoat")
    @ApiOperation("添加渡轮")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bname",value = "渡轮名称",dataType = "String"),
            @ApiImplicitParam(name = "produceDate",value = "渡轮制造日期(年-月-日)",dataType = "String"),
            @ApiImplicitParam(name = "bphoto",value = "渡轮图片",dataType = "String"),
            @ApiImplicitParam(name = "maxPeople",value = "渡轮最大载人数",dataType = "String"),
            @ApiImplicitParam(name = "profile",value = "渡轮简介",dataType = "String")
    })
    public ResultVO addBoat(String bname,String produceDate,String bphoto,Integer maxPeople,String profile) throws ParseException, ConditionNotException {
        Boat boat;
        if(produceDate != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parseDate = sdf.parse(produceDate);
            boat = new Boat(null,bname,parseDate,bphoto,maxPeople,profile);
        }else{
            boat = new Boat(null,bname,null,bphoto,maxPeople,profile);
        }
        iBoatService.addBoat(boat);
        ResultVO resultVO = new ResultVO(ResultStatusEnum.SUCCESS);
        return resultVO;
    }

    @PostMapping("deleteById")
    @ApiOperation("根据渡轮id删除")
    @ApiImplicitParam(name = "id",value = "渡轮编号",dataType = "String")
    public ResultVO deleteById(Integer id) throws ConditionNotException {
        iBoatService.deleteBoatById(id);
        ResultVO resultVO = new ResultVO(ResultStatusEnum.SUCCESS);
        return resultVO;
    }

    @GetMapping("/pageQuery")
    @ApiOperation("分页查询渡轮")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "当前页",dataType = "String",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataType = "String",required = true),
            @ApiImplicitParam(name = "bid",value = "渡轮编号",dataType = "String")
    })
    public ResultVO findByCondition(Integer pageNo,Integer pageSize,Integer bid){
        Integer index = (pageNo - 1)*pageSize;
        BoatVO boatVO = new BoatVO(index,pageSize,bid);
        List<Boat> boats = iBoatService.findByCondition(boatVO);
        int total = iBoatService.findAllCount(boatVO);
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("data",boats);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        ResultVO resultVO = new ResultVO(map);
        return resultVO;
    }
}
