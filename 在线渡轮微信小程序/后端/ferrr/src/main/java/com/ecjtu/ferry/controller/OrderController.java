package com.ecjtu.ferry.controller;

import com.ecjtu.ferry.common.CanNotReturnTicketException;
import com.ecjtu.ferry.common.CanNotSaleException;
import com.ecjtu.ferry.enums.ResultStatusEnum;
import com.ecjtu.ferry.pojo.Order;
import com.ecjtu.ferry.pojo.vo.ResultVO;
import com.ecjtu.ferry.service.Impl.OrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "用户购票相关接口")
@RequestMapping("/consume")
public class OrderController {
    @Autowired
    private OrderServiceImpl iOrderService;

    @PostMapping("/buyTicket")
    @ApiOperation("购票")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid",value = "用户id",dataType = "String",required = true),
            @ApiImplicitParam(name = "tid",value = "票务id",dataType = "String",required = true),
            @ApiImplicitParam(name = "number",value = " 购买数量",dataType = "String"),
    })
    public ResultVO buyTicket(Integer uid,Integer tid,Integer number) throws CanNotSaleException {
        if(number==null){
            //没有传值，默认买一张票
            number=1;
        }
        Order order = new Order();
        order.setUid(uid);
        order.setTid(tid);
        iOrderService.buyTicket(order,number);
        ResultVO resultVO = new ResultVO(ResultStatusEnum.SUCCESS);
        return resultVO;
    }

    @PostMapping("/return")
    @ApiOperation("退票")
    @ApiImplicitParam(name = "oid",value = "已购票的订单编号",dataType = "String",required = true)
    public ResultVO returnTicket(Integer oid) throws CanNotReturnTicketException {
        iOrderService.returnTicket(oid);
        ResultVO resultVO = new ResultVO(ResultStatusEnum.SUCCESS);
        return resultVO;
    }
}
