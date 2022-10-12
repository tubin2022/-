package com.ecjtu.ferry.dao;

import com.ecjtu.ferry.pojo.Boat;
import com.ecjtu.ferry.pojo.vo.BoatVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoatDao {
    /**
     * 添加轮船信息
     * @param boat  轮船信息实体
     */
    public void addBoat(Boat boat);

    /**
     * 根据id删除渡轮
     * @param id    轮船信息主键id
     */
    public void deleteBoatById(Integer id);

    /**
     * 查询渡轮记录总数
     * @return
     */
    public int findAllCount(BoatVO boatVO);

    /**
     * 根据条件分页查询渡轮
     * @param boatVO    封装的条件实体
     * @return
     */
    public List<Boat> findByCondition(BoatVO boatVO);
}
