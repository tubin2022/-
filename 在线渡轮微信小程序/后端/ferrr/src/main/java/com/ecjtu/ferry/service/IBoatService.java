package com.ecjtu.ferry.service;

import com.ecjtu.ferry.common.ConditionNotException;
import com.ecjtu.ferry.pojo.Boat;
import com.ecjtu.ferry.pojo.vo.BoatVO;

import java.util.List;

public interface IBoatService {
    /**
     * 添加渡轮信息
     * @param boat
     * @throws ConditionNotException
     */
    public void addBoat(Boat boat) throws ConditionNotException;

    /**
     * 根据id删除渡轮
     * @param id
     */
    public void deleteBoatById(Integer id) throws ConditionNotException;

    /**
     * 查询渡轮记录总数
     * @return
     */
    public int findAllCount(BoatVO boatVO);

    /**
     *根据条件分页查询渡轮
     * @param boatVO
     * @return
     */
    public List<Boat> findByCondition(BoatVO boatVO);
}
