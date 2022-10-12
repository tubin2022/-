package com.lianwei.controller;

import com.lianwei.domain.dto.DispatchLogisticsDTO;
import com.lianwei.domain.vo.ResultVO;
import com.lianwei.service.DispatchLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dispatchLogistics")
public class DispatchLogisticsController {
    @Autowired
    private DispatchLogisticsService dispatchLogisticsService;

    @GetMapping("/query")
    @CrossOrigin
    public ResultVO findDispatchLogistics(String pro,String time){
        List<DispatchLogisticsDTO> dispatchLogistics = dispatchLogisticsService.findDispatchLogistics(pro, time);
        ResultVO resultVO = new ResultVO(dispatchLogistics);
        return resultVO;
    }
}
