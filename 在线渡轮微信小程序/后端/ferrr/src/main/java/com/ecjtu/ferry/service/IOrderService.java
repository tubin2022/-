package com.ecjtu.ferry.service;

import com.ecjtu.ferry.common.CanNotReturnTicketException;
import com.ecjtu.ferry.common.CanNotSaleException;
import com.ecjtu.ferry.pojo.Order;
import com.ecjtu.ferry.pojo.dto.OrderDTO;
import com.ecjtu.ferry.pojo.vo.OrderVO;

import java.util.List;

public interface IOrderService {
    /**
     * 用户购买船票
     * @param order
     */
    public void buyTicket(Order order,Integer n) throws CanNotSaleException;

    /**
     * 用户退票
     * @param oid  代表订单主键，用于删除订单用
     */
    public void returnTicket(Integer oid) throws CanNotReturnTicketException;

    /**
     * 根据条件分页查询订单
     * @param orderVO
     * @return
     */
    public List<OrderDTO> pageQuery(OrderVO orderVO);

    /**
     * 查询总记录数
     * @param orderVO 封装的条件实体
     * @return
     */
    public int findAllCount(OrderVO orderVO);

    /**
     * 通过订单主键id查询
     * @param oid
     * @return
     */
    public OrderDTO findById(Integer oid);
}
