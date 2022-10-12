package com.lianwei.service;

import com.lianwei.domain.entity.Logistics;
import com.lianwei.domain.vo.ResultVO;
import com.lianwei.domain.vo.SelectLogisticsVO;
import com.lianwei.domain.vo.UpdateLogisticsVO;

import java.util.List;

/**
 * 物流service
 */
public interface LogisticsService {
    /**
     * 用户寄件
     * @param logistics 寄件物流信息
     */
    public void send(Logistics logistics);

    /**
     * 用户派件
     * @param updateLogisticsVO
     */
    public void dispatch(UpdateLogisticsVO updateLogisticsVO);

    /**
     * 用户签收
     * @param logisticsId
     * @param receiveUserName
     * @param receiveUserPhone
     * @return
     */
    public ResultVO receive(Integer logisticsId,String receiveUserName, String receiveUserPhone);

    /**
     * 根据条件分页查询物流信息
     * @param selectLogisticsVO
     * @return
     */
    public ResultVO pageQueryLogistics(SelectLogisticsVO selectLogisticsVO);

    /**
     * 用户退货
     * @param logisticsId
     * @param receiveUserName
     * @param receiveUserPhone
     * @return
     */
    public ResultVO back(Integer logisticsId,String receiveUserName, String receiveUserPhone);
}
