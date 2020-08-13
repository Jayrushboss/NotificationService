package com.example.nibsskeyexchangtrannotification.server.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author JoshuaO
 */
@Component
public class textMessaging {
    // Find your Account Sid and Token at twilio.com/console
    // DANGER! This is insecure. See http://twil.io/secure
    @Value("${account.sid}")
    String ACCOUNT_SID;
    @Value("${auth.token}")
    String AUTH_TOKEN;



    public void smsNotification(String code, String description, String from, String to) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
//                new com.twilio.type.PhoneNumber("+2348169721892"),
                new com.twilio.type.PhoneNumber(to),
                new com.twilio.type.PhoneNumber(from),
                "Dear Team, transactions are failing with "+code +" "+description+". kindly investigate")
                .create();

        System.out.println(message.getSid());
    }
}
