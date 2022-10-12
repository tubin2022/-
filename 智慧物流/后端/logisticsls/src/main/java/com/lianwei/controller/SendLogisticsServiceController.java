package com.lianwei.controller;

import com.lianwei.domain.dto.SendLogisticsDTO;
import com.lianwei.domain.vo.ResultVO;
import com.lianwei.service.SendLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sendLogistics")
public class SendLogisticsServiceController {
    @Autowired
    private SendLogisticsService sendLogisticsService;

    @GetMapping("/query")
    @CrossOrigin
    public ResultVO findSendLogistics(String pro,String time){
        List<SendLogisticsDTO> sendLogistics = sendLogisticsService.findSendLogistics(pro, time);
        ResultVO resultVO = new ResultVO(sendLogistics);
        return resultVO;
    }
}
