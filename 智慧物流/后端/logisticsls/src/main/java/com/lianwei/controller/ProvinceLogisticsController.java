package com.lianwei.controller;

import com.lianwei.domain.vo.ResultVO;
import com.lianwei.service.ProvinceLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provinceLogistics")
public class ProvinceLogisticsController {
    @Autowired
    private ProvinceLogisticsService provinceLogisticsService;

    @GetMapping("/query")
    @CrossOrigin
    public ResultVO findProvinceLogistics(String time,String pro){
        Integer provinceLogistics = provinceLogisticsService.findProvinceLogistics(time, pro);
        ResultVO resultVO = new ResultVO(provinceLogistics);
        return resultVO;
    }
}
