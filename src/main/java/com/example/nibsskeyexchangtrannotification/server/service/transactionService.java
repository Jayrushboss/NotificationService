package com.example.nibsskeyexchangtrannotification.server.service;


import com.example.nibsskeyexchangtrannotification.models.TerminalTransactions;

import java.util.List;

/**
 * @author JoshuaO
 */
public interface transactionService {
    List<TerminalTransactions> findTop10Transactions(int transactionCount);
}
