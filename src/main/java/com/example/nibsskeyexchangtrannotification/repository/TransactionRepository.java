package com.example.nibsskeyexchangtrannotification.repository;

import com.example.nibsskeyexchangtrannotification.models.TerminalTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JoshuaO
 */
@Repository
public interface TransactionRepository extends JpaRepository<TerminalTransactions, Long> {
    @Query(value = "select distinct *\n" +
            "from transaction_logs where tran_complete = 1 and processed = 0 and is_notification = 1",nativeQuery = true)
    List<TerminalTransactions> findAllUnnotifiedTransactions();

    @Query(value = "SELECT TOP (?) * FROM transaction_logs where tran_complete = 1 \n" +
            "order by id desc;",nativeQuery = true)
    List<TerminalTransactions> findTopByTranComplete(int number);
}
