package com.lianwei.service;

import com.lianwei.domain.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {
    public List<TransactionDTO> findSend(String pro,String time);
    public List<TransactionDTO> findDispatch(String pro,String time);
}
