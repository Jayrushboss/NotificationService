package com.example.nibsskeyexchangtrannotification.nibssKeyManagementServer.keymanagement.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

/**
 * @author JoshuaO
 */
@Entity
@Data
public class terminalKeyManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String terminalID;
    private String masterKey;
    private String sessionKey;
    private String pinKey;
    private String parameterDownloaded;
    private String lastExchangeDateTime;
//    private boolean tranComplete = false;
}
