package com.lianwei.controller;

import com.lianwei.domain.vo.ResultVO;
import com.lianwei.service.StatusLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statusLogistics")
public class StatusLogisticsController {
    @Autowired
    private StatusLogisticsService statusLogisticsService;

    @GetMapping("/query")
    @CrossOrigin
    public ResultVO findStatusLogistics(String pro,String time) throws ParseException {
        List<List> statusLogistics = statusLogisticsService.findStatusLogistics(pro, time);

        ResultVO resultVO = new ResultVO(statusLogistics);
        return resultVO;
    }
}
