package com.ecjtu.ferry.service.Impl;

import com.ecjtu.ferry.common.CanNotDeleteShiftException;
import com.ecjtu.ferry.common.ConditionNotException;
import com.ecjtu.ferry.common.RepeatException;
import com.ecjtu.ferry.dao.BoatDao;
import com.ecjtu.ferry.dao.ShiftDao;
import com.ecjtu.ferry.dao.TicketsDao;
import com.ecjtu.ferry.pojo.Boat;
import com.ecjtu.ferry.pojo.Shift;
import com.ecjtu.ferry.pojo.Tickets;
import com.ecjtu.ferry.pojo.dto.TicketsDTO;
import com.ecjtu.ferry.pojo.vo.BoatVO;
import com.ecjtu.ferry.pojo.vo.ShiftVO;
import com.ecjtu.ferry.pojo.vo.TicketsVO;
import com.ecjtu.ferry.service.ITicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TicketServiceImpl implements ITicketsService {
    @Autowired
    private TicketsDao ticketsDao;

    @Autowired
    private BoatDao boatDao;

    @Autowired
    private ShiftDao shiftDao;

    /**
     * 添加票务
     * @param ticket
     */
    @Override
    public void addTicket(Tickets ticket) throws ConditionNotException, RepeatException {
        Integer sid = ticket.getSid();
        Integer bid = ticket.getBid();
        Float price = ticket.getPrice();
        if(sid ==null || bid ==null || price == null){
            throw new ConditionNotException("添加票务信息不能为空");
        }else{
            //判断该渡轮是否已经执行该班次，若已经在执行该班次，返回错误信息，否则继续进行下一步操作
            TicketsVO ticketsVO = new TicketsVO(1,1,bid,sid,null,null,null,null,null);
            List<Tickets> tickets = ticketsDao.findByCondition(ticketsVO);
            if(tickets.size() == 0){
                //该渡轮没有执行该班次，可以继续下一步操作
                //根据前端传来的渡轮id查询该渡轮的信息
                BoatVO boatVO = new BoatVO(0,1,bid);
                List<Boat> boats = boatDao.findByCondition(boatVO);
                Boat boat = boats.get(0);
                //根据前端传来的班次id查询该班次的信息
                ShiftVO shiftVO = new ShiftVO(0,1,sid,null,null,null,null);
                List<Shift> shifts = shiftDao.findByCondition(shiftVO);
                Shift shift = shifts.get(0);
                //根据班次和渡轮信息设置该票务的相关信息
                ticket.setTotal(boat.getMaxPeople());
                ticket.setSurplus(boat.getMaxPeople());
                Date startTime = shift.getStartTime();
                long time = startTime.getTime();
                long startSale = time - 15*24*60*60*1000;
                long endSale = time - 30*60*1000;
                Date startSaleTime = new Date(startSale);
                Date endSaleTime = new Date(endSale);
                ticket.setStartSale(startSaleTime);
                ticket.setEndSale(endSaleTime);
                ticketsDao.addTicket(ticket);
            }else{
                //已存在该渡轮执行该班次,拒绝此次请求，返回错误信息给前端
                throw new RepeatException("存在该渡轮执行该班次的信息");
            }
        }
    }

    /**
     * 根据条件分页查询票务信息
     * @param ticketsVO
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TicketsDTO> pageQueryTicket(TicketsVO ticketsVO) {
        return ticketsDao.pageQueryTicket(ticketsVO);
    }

    /**
     * 根据id修改票务信息
     * @param tickets
     */
    @Override
    public void updateTicket(Tickets tickets) {
        ticketsDao.updateTicket(tickets);
    }

    /**
     * 根据票务id删除票务
     * @param tid
     */
    @Override
    public void deleteTicket(Integer tid) throws CanNotDeleteShiftException {
        TicketsVO ticketsVO = new TicketsVO();
        ticketsVO.setPageNo(0);
        ticketsVO.setPageSize(5);
        ticketsVO.setTid(tid);
        List<TicketsDTO> ticketsDTOS = ticketsDao.pageQueryTicket(ticketsVO);
        TicketsDTO ticketsDTO = ticketsDTOS.get(0);
        Date startTime = ticketsDTO.getShift().getStartTime();
        int i = startTime.compareTo(new Date());
        if(i<0){
            throw new CanNotDeleteShiftException("该班次已发出，拒绝删除");
        }else{
            Integer surplus = ticketsDTO.getSurplus();
            Integer total = ticketsDTO.getTotal();
            if(surplus == total){
                ticketsDao.deleteById(tid);
            }else{
                throw new CanNotDeleteShiftException("该票务已售卖，拒绝删除");
            }
        }
    }

    @Override
    public int findAllCount(TicketsVO ticketsVO) {
        String startTime = ticketsVO.getStartTime();
        if(startTime == null){
            //如果没有传值为空的话，默认是今天的日期
            Date nowDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startTime = sdf.format(nowDate);
        }
        int total = ticketsDao.findAllCount(ticketsVO);
        return total;
    }
}
