package com.ecjtu.ferry.service.Impl;

import com.ecjtu.ferry.common.CanNotReturnTicketException;
import com.ecjtu.ferry.common.CanNotSaleException;
import com.ecjtu.ferry.dao.OrderDao;
import com.ecjtu.ferry.dao.TicketsDao;
import com.ecjtu.ferry.dao.UserDao;
import com.ecjtu.ferry.pojo.Order;
import com.ecjtu.ferry.pojo.Shift;
import com.ecjtu.ferry.pojo.dto.OrderDTO;
import com.ecjtu.ferry.pojo.dto.TicketsDTO;
import com.ecjtu.ferry.pojo.vo.OrderVO;
import com.ecjtu.ferry.pojo.vo.TicketsVO;
import com.ecjtu.ferry.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private TicketsDao ticketsDao;

    @Autowired
    private UserDao userDao;

    /**
     * 用户购买船票
     * @param order
     */
    @Override
    public void buyTicket(Order order,Integer n) throws CanNotSaleException {
        //根据票务id查询票务信息，用于购买后将信息添加到订单表中
        Integer tid = order.getTid();
        TicketsVO ticketsVO = new TicketsVO();
        ticketsVO.setTid(tid);
        ticketsVO.setPageNo(0);
        ticketsVO.setPageSize(1);
        List<TicketsDTO> ticketsDTOS = ticketsDao.pageQueryTicket(ticketsVO);
        TicketsDTO ticketsDTO = ticketsDTOS.get(0);
        //根据查询的票务信息，判断是否处于售卖时间并且剩余票数是否大于1
        Date startSale = ticketsDTO.getStartSale();
        Date endSale = ticketsDTO.getEndSale();
        Integer surplus = ticketsDTO.getSurplus();
        Date now = new Date();
        int i = now.compareTo(startSale);//看是否大于等于起售时间（>=0）
        int i1 = now.compareTo(endSale);//看是否小于等于停售时间(<=0)
        if(i>=0 && i1<=0 && surplus>0){
            //可以售卖
            //获取购买票务的信息，并添加到order对象中
            Shift shift = ticketsDTO.getShift();
            String startCity = shift.getStartCity();
            String endCity = shift.getEndCity();
            Date startTime = shift.getStartTime();
            Date endTime = shift.getEndTime();
            Float price = ticketsDTO.getPrice();
            order.setStartCity(startCity);
            order.setEndCity(endCity);
            order.setStartTime(startTime);
            order.setEndTime(endTime);
            order.setPrice(price);
            //根据用户购买票数，多次进行添加，期间有事务管理
            for(int a =0 ;a<n;a++){
                orderDao.addOrder(order);
                ticketsDao.updateSurplus(-1,tid);
            }
        }else{
            //不能售卖
            throw new CanNotSaleException("该票务暂不进行售卖");
        }
    }

    /**
     * 用户退票
     * @param oid  代表订单主键，用于删除订单用
     */
    @Override
    public void returnTicket(Integer oid) throws CanNotReturnTicketException {
        //根据前端传来的订单id，先查询获取票务id
        OrderDTO order = orderDao.findById(oid);
        Integer tid = order.getTid();
        //根据tid，获取票务信息
        TicketsVO ticketsVO = new TicketsVO();
        ticketsVO.setTid(tid);
        ticketsVO.setPageNo(0);
        ticketsVO.setPageSize(1);
        List<TicketsDTO> ticketsDTOS = ticketsDao.pageQueryTicket(ticketsVO);
        TicketsDTO ticketsDTO = ticketsDTOS.get(0);
        //获取该票务停售时间，判断当前时间是否超过该时间
        Date endSale = ticketsDTO.getEndSale();
        Date now = new Date();
        int i = now.compareTo(endSale);//判断当前时间是否小于停售时间(i<0)
        if(i<0){
            //可以退票
            orderDao.deleteOrder(oid);
            ticketsDao.updateSurplus(1,tid);
        }else{
            //拒绝退票
            throw new CanNotReturnTicketException("当前时间不允许退票");
        }
    }

    /**
     * 根据条件分页查询订单
     * @param orderVO
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> pageQuery(OrderVO orderVO) {
        return orderDao.pageQuery(orderVO);
    }

    /**
     * 查询总记录数
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public int findAllCount(OrderVO orderVO) {
        return orderDao.findAllCount(orderVO);
    }

    /**
     * 通过订单主键id查询
     * @param oid
     * @return
     */
    @Override
    public OrderDTO findById(Integer oid) {
        return orderDao.findById(oid);
    }
}
