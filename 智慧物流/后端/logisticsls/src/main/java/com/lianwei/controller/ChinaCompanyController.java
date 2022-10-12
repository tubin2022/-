package com.lianwei.controller;

import com.lianwei.domain.dto.ChinaCompanyDTO;
import com.lianwei.domain.vo.ResultVO;
import com.lianwei.service.ChinaCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chinaCompany")
public class ChinaCompanyController {
    @Autowired
    private ChinaCompanyService chinaCompanyService;

    @GetMapping("/query")
    @CrossOrigin
    public ResultVO findChinaCompany(String time){
        List<ChinaCompanyDTO> chinaCompany = chinaCompanyService.findChinaCompany(time);
        ResultVO resultVO = new ResultVO(chinaCompany);
        return resultVO;
    }
}
