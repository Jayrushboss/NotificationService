package com.example.nibsskeyexchangtrannotification.models;

import lombok.Data;

import javax.persistence.*;

/**
 * @author JoshuaO
 */

@Entity
@Data
public class keyGen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String terminalID;
    private String masterkey;
    private String sessionkey;
    private String pinkey;
    private String parameters;
    private String encrypted_masterkey;
    private String encrypted_sessionkey;
    private String encrypted_pinkey;
    @OneToOne
    private Institution institution;

}
