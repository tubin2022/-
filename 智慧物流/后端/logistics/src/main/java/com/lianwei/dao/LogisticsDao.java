package com.lianwei.dao;

import com.lianwei.domain.dto.UserLogisticsDTO;
import com.lianwei.domain.entity.Logistics;
import com.lianwei.domain.vo.SelectLogisticsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 物流Dao
 */
@Mapper
public interface LogisticsDao {
    /**
     * 添加物流信息
     * @param logistics
     */
    public void insertOneLogisticsInfo(Logistics logistics);

    /**
     * 根据物流id修改物流信息
     * @param logistics
     */
    public void updateLogisticsInfo(Logistics logistics);

    /**
     * 通过物流id查询物流信息
     * @param logisticsId 物流id
     * @return
     */
    public Logistics findLogisticsById(Integer logisticsId);

    /**
     * 通过条件分页查询物流信息
     * @param selectLogisticsVO
     * @return
     */
    public List<UserLogisticsDTO> pageQueryLogistics(SelectLogisticsVO selectLogisticsVO);

    /**
     * 通过条件查询总记录数
     * @param selectLogisticsVO
     * @return
     */
    public int count(SelectLogisticsVO selectLogisticsVO);
}
