package com.ecjtu.ferry.service.Impl;

import com.ecjtu.ferry.common.ConditionNotException;
import com.ecjtu.ferry.dao.BoatDao;
import com.ecjtu.ferry.dao.TicketsDao;
import com.ecjtu.ferry.pojo.Boat;
import com.ecjtu.ferry.pojo.vo.BoatVO;
import com.ecjtu.ferry.service.IBoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class BoatServiceImpl implements IBoatService {
    @Autowired
    private BoatDao boatDao;

    @Autowired
    private TicketsDao ticketsDao;

    /**
     * 添加渡轮信息
     * @param boat
     * @throws ConditionNotException
     */
    @Override
    public void addBoat(Boat boat) throws ConditionNotException {
        String bname = boat.getBname();
        Date produceDate = boat.getProduceDate();
        String bphoto = boat.getBphoto();
        String profile = boat.getProfile();
        Integer maxPeople = boat.getMaxPeople();
        if(bname == null && profile ==null && bphoto == null && maxPeople == null && produceDate == null){
            throw new ConditionNotException("添加信息不能为空");
        }else{
            boatDao.addBoat(boat);
        }
    }

    /**
     * 根据id删除渡轮
     * @param id
     */
    @Override
    public void deleteBoatById(Integer id) throws ConditionNotException {
        if(id == null){
            throw new ConditionNotException("删除条件不能为空");
        }else{
            ticketsDao.deleteTicket(id,null);
            boatDao.deleteBoatById(id);
        }
    }

    /**
     * 查询渡轮记录总数
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public int findAllCount(BoatVO boatVO) {
        return boatDao.findAllCount(boatVO);
    }

    /**
     * 根据条件分页查询渡轮
     * @param boatVO
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Boat> findByCondition(BoatVO boatVO) {
        return boatDao.findByCondition(boatVO);
    }
}
