package com.lianwei.service.impl;

import com.lianwei.dao.*;
import com.lianwei.domain.entity.OutHouse;
import com.lianwei.service.StatusLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class StatusLogisticsServiceImpl implements StatusLogisticsService {
    @Autowired
    private EnterHouseDao enterHouseDao;

    @Autowired
    private InHouseDao inHouseDao;

    @Autowired
    private OutHouseDao outHouseDao;

    @Autowired
    private LostLogisticsDao logisticsDao;

    @Autowired
    private ReturnLogisticsDao returnLogisticsDao;

    @Override
    public List<List> findStatusLogistics(String pro, String time) throws ParseException {
        List<List> result = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<Integer> value = new ArrayList<>();
        Integer enterHouse = enterHouseDao.findEnterHouse(pro, time);
//        Map<String,Integer> enter = new HashMap<>();
//        enter.put("入库件",enterHouse);
        name.add("入库件");
        value.add(enterHouse);
//        result.add(enter);

        String inTime = null;
        if(time.length() == 10){
            inTime = time;
        }else if (time.length() == 7){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date date = sdf.parse(time);
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            Calendar instance1 = Calendar.getInstance();
            Date now = new Date();
            instance1.setTime(now);
            if(instance.get(Calendar.YEAR) == instance1.get(Calendar.YEAR) && instance.getActualMaximum(Calendar.DAY_OF_MONTH) == instance1.getActualMaximum(Calendar.DAY_OF_MONTH)){
                //当前月份
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                inTime = sdf2.format(now);
            }else{
                int actualMaximum = instance.getActualMaximum(Calendar.DAY_OF_MONTH);
                inTime = time+"-"+actualMaximum;
            }
        }else{
            Calendar instance = Calendar.getInstance();
            Date now = new Date();
            instance.setTime(now);
            int actualMaximum = instance.get(Calendar.YEAR);
            System.out.println(actualMaximum);
            System.out.println(time);
            if(time.equals(Integer.valueOf(actualMaximum).toString())){
                //当前年份
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                inTime = sdf.format(now);
            }else{
                inTime = time+"-12-31";
            }
        }
        Integer inHouse = inHouseDao.findInHouse(pro, inTime);
        if(inHouse < 0){
            inHouse = 0;
        }
//        Map<String,Integer> in = new HashMap<>();
        name.add("在库件");
        value.add(inHouse);
//        result.add(in);

        Integer outHouse = outHouseDao.findOutHouse(pro, time);
        name.add("出库件");
        value.add(outHouse);
//        result.add(out);

        Integer lostLogistics = logisticsDao.findLostLogistics(pro, time);
        name.add("丢失件");
        value.add(lostLogistics);
//        result.add(lost);

        Integer returnLogistics = returnLogisticsDao.findReturnLogistics(pro, time);
        name.add("退签件");
        value.add(returnLogistics);
//        result.add(returns);
        result.add(name);
        result.add(value);
        return result;
    }
}
