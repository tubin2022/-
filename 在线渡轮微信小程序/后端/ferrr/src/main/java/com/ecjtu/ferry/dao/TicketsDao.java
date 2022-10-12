package com.ecjtu.ferry.dao;

import com.ecjtu.ferry.pojo.Tickets;
import com.ecjtu.ferry.pojo.dto.TicketsDTO;
import com.ecjtu.ferry.pojo.vo.TicketsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TicketsDao {

    /**
     * 添加票务
     * @param ticket    票务信息实体
     */
    public void addTicket(Tickets ticket);

    /**
     * 根据班次和渡轮查询票务(主要是后台自己调用)
     * @param ticketsVO     封装的条件实体
     * @return
     */
    public List<Tickets> findByCondition(TicketsVO ticketsVO);

    /**
     * 根据条件分页查询票务
     * @param ticketsVO     封装的条件实体
     * @return
     */
    public List<TicketsDTO> pageQueryTicket(TicketsVO ticketsVO);

    /**
     * 根据票务id修改票务信息
     * @param ticket    票务信息实体
     */
    public void updateTicket(Tickets ticket);

    /**
     * 根据票务id删除票务信息
     * @param tid   票务信息主键id
     */
    public void deleteById(Integer tid);

    /**
     * 根据渡轮id或者班次id删除，用于系统后台内部调用
     * @param bid   渡轮信息主键id
     * @param sid   班次信息主键id
     */
    public void deleteTicket(@Param("bid") Integer bid,@Param("sid") Integer sid);

    /**
     * 剩余票数管理
     * 当用户购票成功或者退票成功需要修改该票务剩余票数
     * @param i 参数i为修改票数剩余量
     */
    public void updateSurplus(@Param("i") Integer i,@Param("tid") Integer tid);

    /**
     * 查询票务总数（这里查询的总数是今天及以后的）
     * @param ticketsVO
     * @return
     */
    public int findAllCount(TicketsVO ticketsVO);
}
