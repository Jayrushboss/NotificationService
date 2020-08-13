package com.example.nibsskeyexchangtrannotification.Notification;

import com.example.nibsskeyexchangtrannotification.models.TerminalTransactions;
import com.example.nibsskeyexchangtrannotification.nibssKeyManagementServer.keymanagement.repository.terminalKeysRepo;
import com.example.nibsskeyexchangtrannotification.repository.TerminalRepository;
import com.example.nibsskeyexchangtrannotification.repository.TransactionRepository;
import org.apache.commons.codec.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

//import static com.jayrush.springmvcrest.freedom.freedomSync.SyncTrans;


/**
 * @author JoshuaO
 */

@EnableScheduling
@Component
public class institutionNotification {
    private static final Logger logger = LoggerFactory.getLogger(institutionNotification.class);


    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TerminalRepository terminalRepository;

    @Autowired
    terminalKeysRepo terminalKeysRepo;

    @Autowired
    NotificationSender NotificationSender;

    @Scheduled(fixedDelay = 10000)
    public void notifyInstitution(){
        logger.info("Starting Batch transaction notification to Institution");
        List<TerminalTransactions> transactions = transactionRepository.findAllUnnotifiedTransactions();
        logger.info("transaction Size is {}",transactions.size());
        if (transactions.isEmpty()){
            logger.info("No Pending Transaction Notification");
        }
        else {
            for (TerminalTransactions transaction : transactions) {
                try {
                    NotificationSender.NotifyInstitution(transaction);
                } catch (IOException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | DecoderException e) {
                    logger.info(e.getMessage());
                }
                TerminalTransactions transactionLog = transactionRepository.findById(transaction.getId()).get();
                transactionLog.setInstitutionResponseCode("00");
                transactionLog.setInstitutionResponseDesc("Flag_Processed");
                transactionLog.setProcessed(true);
                transactionRepository.save(transactionLog);
            }
        }
    }
}


