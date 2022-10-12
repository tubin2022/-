package com.ecjtu.ferry.service;

import com.ecjtu.ferry.common.CanNotDeleteShiftException;
import com.ecjtu.ferry.common.ConditionNotException;
import com.ecjtu.ferry.common.RepeatException;
import com.ecjtu.ferry.pojo.Tickets;
import com.ecjtu.ferry.pojo.dto.TicketsDTO;
import com.ecjtu.ferry.pojo.vo.TicketsVO;

import java.util.List;

public interface ITicketsService {
    /**
     * 添加票务
     * @param ticket
     */
    public void addTicket(Tickets ticket) throws ConditionNotException, RepeatException;

    /**
     * 根据条件分页查询票务信息
     * @param ticketsVO
     * @return
     */
    public List<TicketsDTO> pageQueryTicket(TicketsVO ticketsVO);

    /**
     * 根据id修改票务信息
     * @param tickets
     */
    public void updateTicket(Tickets tickets);

    /**
     * 根据票务id删除票务
     * @param tid
     */
    public void deleteTicket(Integer tid) throws CanNotDeleteShiftException;

    public int findAllCount(TicketsVO ticketsVO);
}
