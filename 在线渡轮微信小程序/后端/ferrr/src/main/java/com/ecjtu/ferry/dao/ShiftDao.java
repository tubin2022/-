package com.ecjtu.ferry.dao;

import com.ecjtu.ferry.pojo.Order;
import com.ecjtu.ferry.pojo.Shift;
import com.ecjtu.ferry.pojo.vo.ShiftVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShiftDao {
    /**
     * 添加班次
     * @param shift     班次实体
     */
    public void addShift(Shift shift);

    /**
     * 根据id删除班次
     * @param id    班次主键id
     */
    public void deleteShift(Integer id);

    /**
     * 查询记录总数
     * @return
     */
    public int findAllCount(ShiftVO shiftVO);

    /**
     * 分页查询班次信息
     * @param shiftVO   封装的条件实体
     * @return
     */
    public List<Shift> findByCondition(ShiftVO shiftVO);

    /**
     * 修改班次信息
     * @param shift     班次信息实体
     */
    public void updateShift(Shift shift);

    /**
     * 根据班次编号查询该趟班次的售票，用于删除班次前判断该班次的票是否售卖
     * 已售卖拒绝删除，未售卖可以删除
     * @param sid
     * @return
     */
    public List<Order> findTicketsByShiftId(Integer sid);
}
