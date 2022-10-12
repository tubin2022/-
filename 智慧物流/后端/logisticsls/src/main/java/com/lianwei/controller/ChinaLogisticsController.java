package com.lianwei.controller;

import com.lianwei.domain.vo.ResultVO;
import com.lianwei.service.ChinaLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chinaLogistics")
public class ChinaLogisticsController {
    @Autowired
    private ChinaLogisticsService chinaLogisticsService;

    @GetMapping("/query")
    @CrossOrigin
    public ResultVO findChinaLogistics(String time){
        Integer chinaLogistics = chinaLogisticsService.findChinaLogistics(time);
        ResultVO resultVO = new ResultVO(chinaLogistics);
        return resultVO;
    }
}
