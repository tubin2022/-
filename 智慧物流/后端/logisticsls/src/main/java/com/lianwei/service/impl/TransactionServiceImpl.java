package com.lianwei.service.impl;

import com.lianwei.dao.TransactionDao;
import com.lianwei.domain.dto.TransactionDTO;
import com.lianwei.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionDao transactionDao;

    @Override
    public List<TransactionDTO> findSend(String pro, String time) {
        return transactionDao.findSend(pro, time);
    }

    @Override
    public List<TransactionDTO> findDispatch(String pro, String time) {
        return transactionDao.findDispatch(pro, time);
    }
}
