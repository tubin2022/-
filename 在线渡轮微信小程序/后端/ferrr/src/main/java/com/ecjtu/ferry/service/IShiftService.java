package com.ecjtu.ferry.service;

import com.ecjtu.ferry.common.CanNotDeleteShiftException;
import com.ecjtu.ferry.common.ConditionNotException;
import com.ecjtu.ferry.pojo.Shift;
import com.ecjtu.ferry.pojo.vo.ResultVO;
import com.ecjtu.ferry.pojo.vo.ShiftVO;

public interface IShiftService {
    /**
     * 添加班次信息
     * @param shift
     */
    public void addShift(Shift shift) throws ConditionNotException;

    /**
     * 根据id删除班次
     * @param id
     */
    public void deleteShift(Integer id) throws ConditionNotException, CanNotDeleteShiftException;

    /**
     * 分页查询班次信息
     * @param shiftVO
     * @return
     */
    public ResultVO pageQueryShift(ShiftVO shiftVO);

    /**
     * 修改班次信息
     * @param shift
     * @throws ConditionNotException
     */
    public void updateShift(Shift shift) throws ConditionNotException;
}
