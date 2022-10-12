package com.ecjtu.ferry.service.Impl;

import com.ecjtu.ferry.common.ConditionNotException;
import com.ecjtu.ferry.dao.StatisticsDao;
import com.ecjtu.ferry.pojo.Shift;
import com.ecjtu.ferry.pojo.dto.TicketsDTO;
import com.ecjtu.ferry.pojo.vo.ResultVO;
import com.ecjtu.ferry.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StatisticsServiceImpl implements IStatisticsService {
    @Autowired
    private StatisticsDao statisticsDao;

    /**
     * 根据日期查询每天售票情况
     * 参数date为前端格式化的字符串日期参数，例如：2022-06-22
     * @param date
     * @return
     */
    @Override
    public ResultVO everyDaySales(String date) throws ConditionNotException {
        //判断查询条件
        if(date != null){
           date = "%"+date+"%";
        }else{
            throw new ConditionNotException("查询操作条件不能为空");
        }
        //获取某天的所有票务信息，主要包含从哪到哪，卖了多少张票，还剩多少张票
        List<TicketsDTO> ticketsDTOS = statisticsDao.everyDaySales(date);
        //定义容器，存储相关信息
        List<Map<String,Object>> info = new ArrayList<>();
        //遍历查询得到的票务信息，组装需要的信息，存储到容器中
        for(TicketsDTO ticketsDTO : ticketsDTOS){
            Map<String,Object> map = new HashMap<>();
            Integer total = ticketsDTO.getTotal();//票总数
            Integer surplus = ticketsDTO.getSurplus();//剩余票数
            Integer saled = total - surplus;//已卖票数
            String startCity = ticketsDTO.getShift().getStartCity();//出发城市
            String endCity = ticketsDTO.getShift().getEndCity();//到达城市
            String line = startCity+"-"+endCity;
            map.put("line",line);
            map.put("saled",saled);
            map.put("surplus",surplus);
            info.add(map);
        }
        //到这，需要将相同路线的票进行去重合并，即已卖票数和未卖票数相加
        List<Map<String,Object>> result = new ArrayList<>();
        int length = info.size();
        for(int i =0;i<length;i++){
            Map<String, Object> obj = info.get(i);
            Integer saled = (Integer) obj.get("saled");
            Integer surplus = (Integer) obj.get("surplus");
            for(int j = i+1;j<length;j++){
                Map<String, Object> obj2 = info.get(j);
                if(obj.get("line").equals(obj2.get("line"))){
                    //路线相同进行合并
                    Integer saled2 = (Integer) obj2.get("saled");
                    Integer surplus2 = (Integer) obj2.get("surplus");
                    saled = saled+saled2;
                    surplus = surplus+surplus2;
                    //路线相同的进行去重
                    info.remove(j);
                    length = info.size();
                }
            }
            //将每次循环（已去重的添加到result容器中）
            obj.put("saled",saled);
            obj.put("surplus",surplus);
            result.add(obj);
        }
        //最后将结果组装成前端需要的数据格式
        List<String> line = new ArrayList<>();//存储路线（对应前端柱状图的横坐标）
        List<Integer> saled = new ArrayList<>();//已售票数（对应前端柱状图的数据）
        List<Integer> surplus = new ArrayList<>();//未售票数（对应前端柱状图的数据）
        for(Map m :result){
            line.add((String)m.get("line"));
            saled.add((Integer)m.get("saled"));
            surplus.add((Integer)m.get("surplus"));
        }
        Map<String,List> response = new HashMap<>();
        response.put("line",line);
        response.put("saled",saled);
        response.put("surplus",surplus);
        ResultVO resultVO = new ResultVO(response);
        return resultVO;
    }


    /**
     * 根据日期查询每个城市每天总共发了多少趟班次
     * 参数date为前端格式化的字符串日期参数，例如：2022-06-22
     * @param date
     * @return
     */
    @Override
    public ResultVO everyDayShifts(String date) throws ConditionNotException {
        //判断查询条件
        if(date != null){
            date = "%"+date+"%";
        }else{
            throw new ConditionNotException("查询操作条件不能为空");
        }
        //查询某天的班次信息
        List<Shift> shifts = statisticsDao.everyDayShift(date);
        //定义容器存储组装的前端需要指定格式的数据
        List<Map<String,Object>> result = new ArrayList<>();
        int length = shifts.size();
        for(int i =0;i<length;i++){
            Shift obj = shifts.get(i);
            int total = 1;
            for(int j =i+1;j<length;j++){
                Shift obj2 = shifts.get(j);
                if(obj.getStartCity().equals(obj2.getStartCity())){
                    //发出班次城市相同，total+1
                    total++;
                    //删除容器中相同的元素
                    shifts.remove(j);
                    j--;
                    length = shifts.size();
                }
            }
            System.out.println(shifts);
            Map<String,Object> map = new HashMap<>();
            map.put("value",total);
            map.put("name",shifts.get(i).getStartCity());
            result.add(map);
        }
        ResultVO resultVO =  new ResultVO(result);
        return resultVO;
    }
}
