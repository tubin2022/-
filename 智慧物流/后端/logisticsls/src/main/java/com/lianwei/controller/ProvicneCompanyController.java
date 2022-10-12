package com.lianwei.controller;

import com.lianwei.domain.dto.ProvinceCompanyDTO;
import com.lianwei.domain.vo.ResultVO;
import com.lianwei.service.ProvinceCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/provinceCompany")
public class ProvicneCompanyController {
    @Autowired
    private ProvinceCompanyService provinceCompanyService;

    @GetMapping("/query")
    @CrossOrigin
    public ResultVO findProvinceCompany(String time,String pro){
        List<ProvinceCompanyDTO> provinceCompany = provinceCompanyService.findProvinceCompany(time, pro);
        ResultVO resultVO = new ResultVO(provinceCompany);
        return resultVO;
    }
}
