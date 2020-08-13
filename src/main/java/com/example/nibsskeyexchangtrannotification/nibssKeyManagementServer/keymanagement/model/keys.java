package com.example.nibsskeyexchangtrannotification.nibssKeyManagementServer.keymanagement.model;

import lombok.Data;

/**
 * @author JoshuaO
 */

@Data
public class keys {
    private String masterkey;
    private String sessionKey;
    private String pinKey;
    private String parameters;
}
