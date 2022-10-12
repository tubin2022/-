package com.ecjtu.ferry.controller;

import com.ecjtu.ferry.pojo.Order;
import com.ecjtu.ferry.pojo.dto.OrderDTO;
import com.ecjtu.ferry.pojo.vo.OrderVO;
import com.ecjtu.ferry.pojo.vo.ResultVO;
import com.ecjtu.ferry.service.Impl.OrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Api(tags = "用户订单管理相关接口")
public class OrderSelectController {
    @Autowired
    private OrderServiceImpl iOrderService;

    @GetMapping("/pageQuery")
    @ApiOperation("分页查询订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "当前页",dataType = "String",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataType = "String",required = true),
            @ApiImplicitParam(name = "uid",value = "用户id",dataType = "String"),
            @ApiImplicitParam(name = "tid",value = "票务id",dataType = "String"),
            @ApiImplicitParam(name = "startCity",value = "出发城市",dataType = "String"),
            @ApiImplicitParam(name = "arriveCity",value = "到达城市",dataType = "String"),
            @ApiImplicitParam(name = "status",value = "订单状态(已完成,待出行)",dataType = "String")
    })
    public ResultVO pageQueryOrder(Integer pageNo,Integer pageSize,Integer uid,Integer tid,String startCity,String arriveCity,String status ){
        Integer index = (pageNo - 1)*pageSize;
        if(startCity != null){
            startCity = "%"+startCity+"%";
        }
        if(arriveCity != null){
            arriveCity = "%"+arriveCity+"%";
        }
        OrderVO orderVO = new OrderVO(index,pageSize,null,uid,tid,startCity,arriveCity,status);
        List<OrderDTO> orderDTOS = iOrderService.pageQuery(orderVO);
        Map<String,Object> map = new HashMap<>();
        map.put("data",orderDTOS);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        int total = iOrderService.findAllCount(orderVO);
        map.put("total",total);
        ResultVO resultVO = new ResultVO(map);
        return resultVO;
    }

    @GetMapping("/findById")
    @ApiOperation("通过id查询订单详情")
    @ApiImplicitParam(name = "oid",value = "订单id",dataType = "String")
    public ResultVO findById(Integer oid){
        OrderDTO order = iOrderService.findById(oid);
        ResultVO resultVO = new ResultVO(order);
        return resultVO;
    }
}
