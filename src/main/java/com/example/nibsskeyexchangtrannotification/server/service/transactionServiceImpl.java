package com.example.nibsskeyexchangtrannotification.server.service;

import com.example.nibsskeyexchangtrannotification.models.TerminalTransactions;
import com.example.nibsskeyexchangtrannotification.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JoshuaO
 */
@Service
public class transactionServiceImpl implements transactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<TerminalTransactions> findTop10Transactions(int transactionCount) {
        return transactionRepository.findTopByTranComplete(transactionCount);
    }
}
