package com.example.nibsskeyexchangtrannotification.server.service;

import com.example.nibsskeyexchangtrannotification.email.service.MailService;
import com.example.nibsskeyexchangtrannotification.models.TerminalTransactions;
import com.example.nibsskeyexchangtrannotification.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JoshuaO
 */

@EnableScheduling
@Component
public class Notification {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    transactionService transactionService;
    @Autowired
    MailService mailService;
    @Autowired
    textMessaging textMessaging;
    @Value("${mail.to}")
    String mailTo;
    @Value("${query.number}")
    int queryNumber;
    @Value("${sms.from}")
    String from;
    @Value("${sms.to}")
    String to;
    @Value("${Cc}")
    String Cc;
    @Value("${Cc2}")
    String CC2;

    private static Logger logger = LoggerFactory.getLogger(Notification.class);

    @Scheduled(fixedDelayString = "${notification.time}")
    public void startService(){

        logger.info("Starting Service check...");


        List<TerminalTransactions> TransactionList = transactionRepository.findTopByTranComplete(queryNumber);
        Map<String, Object> params = new HashMap<>();
        String[] copy = new String[2];
        copy[0] = Cc;
        copy[1] = CC2;


        int iswcount91 = 0;
        int iswcount61 = 0;
        int nibsscount91 = 0;
        int nibsscount61 = 0;
        int timeout = 0;
        for (int i = 0;i<TransactionList.size();i++){
            if ((TransactionList.get(i).getResponseCode().equals("91"))&&(TransactionList.get(i).getProcessedBy().equals("INTERSWITCH"))){
                iswcount91 = iswcount91+1;
            }
            if ((TransactionList.get(i).getResponseCode().equals("61"))&&(TransactionList.get(i).getProcessedBy().equals("INTERSWITCH"))){
                iswcount61 = iswcount61+1;
            }
            if ((TransactionList.get(i).getResponseCode().equals("61"))&&(TransactionList.get(i).getProcessedBy().equals("NIBSS"))){
                nibsscount61 = nibsscount61+1;
            }
            if ((TransactionList.get(i).getResponseCode().equals("91"))&&(TransactionList.get(i).getProcessedBy().equals("NIBSS"))){
                nibsscount61 = nibsscount61+1;
            }
            if ((TransactionList.get(i).getResponseCode().equals("-1"))&&(TransactionList.get(i).getResponseDesc().equals("Transaction Timed out"))){
                timeout = timeout+1;
            }
        }
        switch (iswcount61){
            case 5:
                try {
//                    textMessaging.smsNotification(TransactionList.get(1).getResponseCode(),TransactionList.get(1).getResponseDesc(),from,to);
                    params.put("responseCode",TransactionList.get(1).getResponseCode());
                    params.put("responseDesc",TransactionList.get(1).getResponseDesc());
                    params.put("processedBy","INTERSWITCH");
                    mailService.sendMail("[URGENT] MEDUSA SERVICE ATTENTION",mailTo ,copy,params,"user_template","3LINE CARD MANAGEMENT");
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info(e.getMessage());
                }
                break;
            default:
                break;
        }
        switch (iswcount91){
            case 5:
                try {
                    //                    textMessaging.smsNotification(TransactionList.get(1).getResponseCode(),TransactionList.get(1).getResponseDesc(),from,to);
                    params.put("responseCode",TransactionList.get(1).getResponseCode());
                    params.put("responseDesc",TransactionList.get(1).getResponseDesc());
                    params.put("processedBy","INTERSWITCH");
                    mailService.sendMail("[URGENT] MEDUSA SERVICE ATTENTION",mailTo ,copy,params,"user_template","3LINE CARD MANAGEMENT");
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info(e.getMessage());
                }
                break;
            default:
                break;
        }

        switch (nibsscount91){
            case 5:
                try {
                    String[] copy2 = new String[1];
                    copy2[0] = Cc;
//                    textMessaging.smsNotification(TransactionList.get(1).getResponseCode(),TransactionList.get(1).getResponseDesc(),from,to);
                    params.put("responseCode",TransactionList.get(1).getResponseCode());
                    params.put("responseDesc",TransactionList.get(1).getResponseDesc());
                    params.put("processedBy","NIBSS");
                    mailService.sendMail("[URGENT] MEDUSA SERVICE ATTENTION",mailTo ,copy2,params,"user_template","3LINE CARD MANAGEMENT");
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info(e.getMessage());
                }
                break;
            default:
                break;
        }
        switch (nibsscount61){
            case 5:
                try {
                    String[] copy2 = new String[1];
                    copy2[0] = Cc;
//                    textMessaging.smsNotification(TransactionList.get(1).getResponseCode(),TransactionList.get(1).getResponseDesc(),from,to);
                    params.put("responseCode",TransactionList.get(1).getResponseCode());
                    params.put("responseDesc",TransactionList.get(1).getResponseDesc());
                    params.put("processedBy","NIBSS");
                    mailService.sendMail("[URGENT] MEDUSA SERVICE ATTENTION",mailTo ,copy2,params,"user_template","3LINE CARD MANAGEMENT");
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info(e.getMessage());
                }
                break;
            default:
                break;
        }
        switch (timeout){
            case 5:
                try {
                    String[] copy2 = new String[1];
                    copy2[0] = Cc;
//                    textMessaging.smsNotification(TransactionList.get(1).getResponseCode(),TransactionList.get(1).getResponseDesc(),from,to);
                    params.put("responseCode",TransactionList.get(1).getResponseCode());
                    params.put("responseDesc",TransactionList.get(1).getResponseDesc());
                    params.put("processedBy",TransactionList.get(1).getProcessedBy());
                    mailService.sendMail("[URGENT] MEDUSA SERVICE ATTENTION",mailTo ,copy2,params,"user_template","3LINE CARD MANAGEMENT");
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info(e.getMessage());
                }
                break;
            default:
                break;
        }
    }
}
