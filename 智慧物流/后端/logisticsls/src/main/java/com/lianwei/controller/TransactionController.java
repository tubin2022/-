package com.lianwei.controller;

import com.lianwei.domain.dto.TransactionDTO;
import com.lianwei.domain.vo.ResultVO;
import com.lianwei.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/send")
    @CrossOrigin
    public ResultVO findSend(String pro,String time){
        List<TransactionDTO> send = transactionService.findSend(pro, time);
        ResultVO resultVO = new ResultVO(send);
        return resultVO;
    }

    @GetMapping("/dispatch")
    @CrossOrigin
    public ResultVO findDispatch(String pro,String time){
        List<TransactionDTO> send = transactionService.findDispatch(pro, time);
        ResultVO resultVO = new ResultVO(send);
        return resultVO;
    }
}
