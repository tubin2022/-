package com.lianwei.service.impl;

import com.lianwei.dao.LogisticsDao;
import com.lianwei.domain.dto.UserLogisticsDTO;
import com.lianwei.domain.entity.Logistics;
import com.lianwei.domain.vo.ResultVO;
import com.lianwei.domain.vo.SelectLogisticsVO;
import com.lianwei.domain.vo.UpdateLogisticsVO;
import com.lianwei.enums.ResultStatusEnum;
import com.lianwei.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 物流service实现类
 */
@Service
@Transactional
public class LogisticsServiceImpl implements LogisticsService {
    @Autowired
    private LogisticsDao logisticsDao;
    /**
     * 用户寄件
     * @param logistics 寄件物流信息
     */
    @Override
    public void send(Logistics logistics) {
        //设置物流单号
        String[] split = UUID.randomUUID().toString().split("-");
        String logisticsNumber = System.currentTimeMillis()+split[split.length-1];
        logistics.setLogisticsNumber(logisticsNumber.substring(6,24));
        //设置物流状态
        logistics.setLogisticsStatus("派件中");
        //设置寄件时间
        logistics.setSendTime(new Date());
        //设置到达下一地的时间，这里为出发地
        List<Date> passTime = new ArrayList<>();
        passTime.add(new Date());
        logistics.setPassTime(passTime);
        //将出发地加入到途径路程中
        List<BigDecimal> sendAddress = logistics.getSendAddress();
        List<List<BigDecimal>> passAddress = new ArrayList<>();
        passAddress.add(sendAddress);
        logistics.setPassAddresses(passAddress);
        logisticsDao.insertOneLogisticsInfo(logistics);
    }

    /**
     * 用户派件
     * @param updateLogisticsVO
     */
    @Override
    public void dispatch(UpdateLogisticsVO updateLogisticsVO) {
        List<BigDecimal> arrive = updateLogisticsVO.getPassAddress();
        Integer dispatchUserId1 = updateLogisticsVO.getDispatchUserId();
        Integer logisticsId = updateLogisticsVO.getLogisticsId();
        Logistics logisticsById = logisticsDao.findLogisticsById(logisticsId);
        //已经经过的地址
        List<List<BigDecimal>> passedAddress = logisticsById.getPassAddresses();
        //将新到达的地址加入到已经过的地址中
        if(passedAddress != null){
            passedAddress.add(arrive);
        }else{
            passedAddress = new ArrayList<>();
            passedAddress.add(arrive);
        }
        //已经经过的地址的时间
        List<Date> passTime = logisticsById.getPassTime();
        //将最新到达地点的时间加入到原先的集合中
        if(passTime != null){
            passTime.add(new Date());
        }else{
            passTime = new ArrayList<>();
            passTime.add(new Date());
        }
        //获取派件人信息，将此次派件人的id加入到派件人信息中
        List<Integer> dispatchUserId = logisticsById.getDispatchUserId();
        if(dispatchUserId != null){
            dispatchUserId.add(dispatchUserId1);
        }else{
            dispatchUserId = new ArrayList<>();
            dispatchUserId.add(dispatchUserId1);
        }
        //构建修改物流对象
        Logistics logistics = new Logistics();
        logistics.setPassAddresses(passedAddress);
        logistics.setPassTime(passTime);
        logistics.setDispatchUserId(dispatchUserId);
        logistics.setLogisticsId(updateLogisticsVO.getLogisticsId());
        //判断物流是否送到收件点，如果到了，修改物流状态，修改为待签收
        List<BigDecimal> receiveAddress = logisticsById.getReceiveAddress();
        if(receiveAddress.equals(arrive)){
            logistics.setLogisticsStatus("待签收");
        }
        logisticsDao.updateLogisticsInfo(logistics);
    }

    /**
     * 用户签收
     * @param logisticsId
     * @param receiveUserPhone
     */
    @Override
    public ResultVO receive(Integer logisticsId,String receiveUserName,String receiveUserPhone) {
        Logistics logisticsById = logisticsDao.findLogisticsById(logisticsId);
        //获取物流中寄件人指定的收件人联系电话
        String receiveUserPhone1 = logisticsById.getReceiveUserPhone();
        String receiveUserName1 = logisticsById.getReceiveUserName();
        //比较两者，只有两者相同才能签收
        if(receiveUserPhone1.equals(receiveUserPhone) && receiveUserName1.equals(receiveUserName)){
            Logistics logistics = new Logistics();
            logistics.setLogisticsId(logisticsId);
            logistics.setLogisticsStatus("已签收");
            logistics.setReceiveTime(new Date());
            logisticsDao.updateLogisticsInfo(logistics);
            return new ResultVO();
        }else{
            ResultVO resultVO1 = new ResultVO(ResultStatusEnum.LOGISTICS_ERROR_RECEIVE_USER);
            return resultVO1;
        }
    }

    /**
     * 根据条件分页查询物流信息
     * @param selectLogisticsVO
     * @return
     */
    @Override
    public ResultVO pageQueryLogistics(SelectLogisticsVO selectLogisticsVO) {
        List<UserLogisticsDTO> logistics = logisticsDao.pageQueryLogistics(selectLogisticsVO);
        int count = logisticsDao.count(selectLogisticsVO);
        Map<String,Object> map = new HashMap<>();
        map.put("data",logistics);
        map.put("count",count);
        ResultVO resultVO = new ResultVO(map);
        return resultVO;
    }

    /**
     * 用户退货
     * @param logisticsId
     * @param receiveUserName
     * @param receiveUserPhone
     * @return
     */
    @Override
    public ResultVO back(Integer logisticsId, String receiveUserName, String receiveUserPhone) {
        Logistics logisticsById = logisticsDao.findLogisticsById(logisticsId);
        //获取物流中寄件人指定的收件人联系电话
        String receiveUserPhone1 = logisticsById.getReceiveUserPhone();
        String receiveUserName1 = logisticsById.getReceiveUserName();
        //比较两者，只有两者相同才能签收
        if(receiveUserPhone1.equals(receiveUserPhone) && receiveUserName1.equals(receiveUserName)){
            Logistics logistics = new Logistics();
            logistics.setLogisticsId(logisticsId);
            logistics.setLogisticsStatus("退货");
            logistics.setReceiveTime(new Date());
            logisticsDao.updateLogisticsInfo(logistics);
            return new ResultVO();
        }else{
            ResultVO resultVO1 = new ResultVO(ResultStatusEnum.LOGISTICS_ERROR_RECEIVE_USER);
            return resultVO1;
        }
    }
}
