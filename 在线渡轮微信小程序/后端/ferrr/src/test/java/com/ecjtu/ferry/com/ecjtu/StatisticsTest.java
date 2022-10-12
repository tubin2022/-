package com.ecjtu.ferry.com.ecjtu;

import com.ecjtu.ferry.FerrrApplication;
import com.ecjtu.ferry.common.ConditionNotException;
import com.ecjtu.ferry.dao.StatisticsDao;
import com.ecjtu.ferry.pojo.dto.TicketsDTO;
import com.ecjtu.ferry.pojo.vo.ResultVO;
import com.ecjtu.ferry.service.Impl.StatisticsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FerrrApplication.class)
public class StatisticsTest {
    @Autowired
    private StatisticsDao statisticsDao;

    @Autowired
    private StatisticsServiceImpl statisticsService;
    @Test
    public void test(){
        List<TicketsDTO> ticketsDTOS = statisticsDao.everyDaySales("%2022-06-20%");
        System.out.println(ticketsDTOS);
    }

    @Test
    public void test2() throws ConditionNotException {
        ResultVO resultVO = statisticsService.everyDaySales("2022-06-20");
        System.out.println(resultVO);
    }

    @Test
    public void test3() throws ConditionNotException {
        ResultVO resultVO = statisticsService.everyDayShifts("2022-06-20");
        System.out.println(resultVO);
    }
}
