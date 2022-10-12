package com.ecjtu.ferry.service.Impl;

import com.ecjtu.ferry.common.CanNotDeleteShiftException;
import com.ecjtu.ferry.common.ConditionNotException;
import com.ecjtu.ferry.dao.ShiftDao;
import com.ecjtu.ferry.dao.TicketsDao;
import com.ecjtu.ferry.pojo.Order;
import com.ecjtu.ferry.pojo.Shift;
import com.ecjtu.ferry.pojo.vo.ResultVO;
import com.ecjtu.ferry.pojo.vo.ShiftVO;
import com.ecjtu.ferry.service.IShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShiftServiceImpl implements IShiftService {
    @Autowired
    private ShiftDao shiftDao;

    @Autowired
    private TicketsDao ticketsDao;

    /**
     * 添加班次信息
     * @param shift
     */
    @Override
    public void addShift(Shift shift) throws ConditionNotException {
        String startCity = shift.getStartCity();
        String endCity = shift.getEndCity();
        Date startTime = shift.getStartTime();
        Date endTime = shift.getEndTime();
        if(startCity == null || endCity == null || startTime == null || endTime == null){
            throw new ConditionNotException("添加条件不能为空");
        }else{
            shiftDao.addShift(shift);
        }
    }

    /**
     * 根据id删除班次
     * @param id
     */
    @Override
    public void deleteShift(Integer id) throws ConditionNotException, CanNotDeleteShiftException {
        if(id == null){
            throw new ConditionNotException("删除id不能为空");
        }else{
            //根据班次id查找该班次信息判断该班次是否发出，若已发出不能修改，没有发出可以修改
            ShiftVO shiftVO = new ShiftVO();
            shiftVO.setPageNo(0);
            shiftVO.setPageSize(5);
            shiftVO.setSid(id);
            List<Shift> shifts = shiftDao.findByCondition(shiftVO);
            Shift shift = shifts.get(0);
            Date startTime = shift.getStartTime();
            int i = startTime.compareTo(new Date());
            if(i<0){
                //班次已发出，拒绝删除
                throw new CanNotDeleteShiftException("该班次已发出，拒绝删除");
            }else{
                //判断该班次的票是否售卖，以售卖拒绝删除，否则可以删除
                List<Order> tickets = shiftDao.findTicketsByShiftId(id);
                if(tickets.size()>0){
                    throw new CanNotDeleteShiftException("该班次已经进行售票，决绝删除");
                }else{
                    shiftDao.deleteShift(id);
                }
            }
        }
    }

    /**
     * 分页查询班次信息
     * @param shiftVO
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResultVO pageQueryShift(ShiftVO shiftVO) {
        int total = shiftDao.findAllCount(shiftVO);
        List<Shift> shifts = shiftDao.findByCondition(shiftVO);
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("data",shifts);
        map.put("pageNo",(shiftVO.getPageNo()/shiftVO.getPageSize())+1);
        map.put("pageSize",shiftVO.getPageSize());
        ResultVO resultVO = new ResultVO(map);
        return resultVO;
    }

    /**
     * 修改班次信息
     * @param shift
     * @throws ConditionNotException
     */
    @Override
    public void updateShift(Shift shift) throws ConditionNotException {
        String endCity = shift.getEndCity();
        String startCity = shift.getStartCity();
        Date startTime = shift.getStartTime();
        Date endTime = shift.getEndTime();
        if(startCity ==null && endCity==null && startTime ==null && endTime == null){
            throw new ConditionNotException("修改不能为空");
        }else{
            shiftDao.updateShift(shift);
        }
    }
}
