package com.ecjtu.ferry.controller;

import com.ecjtu.ferry.common.CanNotDeleteShiftException;
import com.ecjtu.ferry.common.ConditionNotException;
import com.ecjtu.ferry.common.RepeatException;
import com.ecjtu.ferry.enums.ResultStatusEnum;
import com.ecjtu.ferry.pojo.Tickets;
import com.ecjtu.ferry.pojo.dto.TicketsDTO;
import com.ecjtu.ferry.pojo.vo.ResultVO;
import com.ecjtu.ferry.pojo.vo.TicketsVO;
import com.ecjtu.ferry.service.Impl.TicketServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "票务管理相关接口")
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private TicketServiceImpl iTicketService;

    @PostMapping("/addTicket")
    @ApiOperation("添加票务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bid",value = "渡轮id,选择渡轮执行班次",dataType = "String",required = true),
            @ApiImplicitParam(name = "sid",value = "班次id",dataType = "String",required = true),
            @ApiImplicitParam(name = "price",value = "每张票的价格",dataType = "String",required = true),
    })
    public ResultVO addTicket(Integer bid,Integer sid,Float price) throws ConditionNotException, RepeatException {
        Tickets ticket = new Tickets();
        ticket.setBid(bid);
        ticket.setSid(sid);
        ticket.setPrice(price);
        iTicketService.addTicket(ticket);
        ResultVO resultVO = new ResultVO(ResultStatusEnum.SUCCESS);
        return resultVO;
    }

    @GetMapping("/pageQueryTicket")
    @ApiOperation("分页查询票务信息")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "pageNo",value = "当前页",dataType = "String",required = true),
           @ApiImplicitParam(name = "pageSize",value = "每页大小",dataType = "String",required = true),
           @ApiImplicitParam(name = "startCity",value = "出发城市",dataType = "String"),
           @ApiImplicitParam(name = "arriveCity",value = "到达城市",dataType = "String"),
           @ApiImplicitParam(name = "startTime",value = "出发时间",dataType = "String"),
           @ApiImplicitParam(name = "arriveTime",value = "到达时间",dataType = "String"),
    })
    public ResultVO pageQuery(Integer pageNo,Integer pageSize,String startCity,String arriveCity,String startTime,String arriveTime){
        Integer index = (pageNo-1)*pageSize;
        if(startCity != null && startCity !=""){
            startCity = "%"+startCity+"%";
        }
        if(arriveCity != null && arriveCity !=""){
            arriveCity = "%"+arriveCity+"%";
        }
//        if(startTime == null){
//            Date now = new Date();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            startTime = sdf.format(now);
//        }
        if(startTime != null && startTime !=""){
            startTime = "%"+startTime+"%";
        }else{
            startTime = null;
        }
        TicketsVO ticketsVO = new TicketsVO(index,pageSize,null,null,startCity,arriveCity,startTime,arriveTime,null);
        List<TicketsDTO> tickets = iTicketService.pageQueryTicket(ticketsVO);
        Map<String,Object> map = new HashMap<>();
        map.put("data",tickets);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        int total = iTicketService.findAllCount(ticketsVO);
        map.put("total",total);
        ResultVO resultVO = new ResultVO(map);
        return resultVO;
    }

    @PostMapping("/updateTicket")
    @ApiOperation("根据id修改票务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tid",value = "票务id",dataType = "String",required = true),
            @ApiImplicitParam(name = "bid",value = "渡轮id",dataType = "String"),
            @ApiImplicitParam(name = "price",value = "票务价格",dataType = "String"),
    })
    public ResultVO updateTicket(Integer tid,Integer bid,Float price){
        Tickets tickets = new Tickets();
        tickets.setTid(tid);
        tickets.setBid(bid);
        tickets.setPrice(price);
        iTicketService.updateTicket(tickets);
        ResultVO resultVO = new ResultVO(ResultStatusEnum.SUCCESS);
        return resultVO;
    }

    @PostMapping("/deleteTicket")
    @ApiOperation("根据id删除票务")
    @ApiImplicitParam(name = "tid",value = "票务id",dataType = "String",required = true)
    public ResultVO deleteTicket(Integer tid) throws CanNotDeleteShiftException {
        iTicketService.deleteTicket(tid);
        ResultVO resultVO = new ResultVO(ResultStatusEnum.SUCCESS);
        return resultVO;
    }
}
