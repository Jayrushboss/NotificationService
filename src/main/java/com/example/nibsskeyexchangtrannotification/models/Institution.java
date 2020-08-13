package com.example.nibsskeyexchangtrannotification.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String institutionID;
    private String institutionName;
    private String institutionEmail;
    private String institutionPhone;
    private String settlementAccount;
    private String createdBy;
    private String dateCreated;

    @OneToOne(cascade = CascadeType.ALL)
    private serviceProviders serviceProviders;

    private String bank;

    private boolean saved = false;
    private String savedDescription;
    private String institutionURL;
    private String institutionAppKey;
    private String institutionIntegrationVersion;
    private Boolean globalSetting = false;
    private String component1;
    private String component2;
//    private Boolean isKeyMgt;
    private Boolean isnotification;
    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", institutionID='" + institutionID + '\'' +
                ", institutionName='" + institutionName + '\'' +
                ", institutionEmail='" + institutionEmail + '\'' +
                ", institutionPhone='" + institutionPhone + '\'' +
                ", settlementAccount=" + settlementAccount +
                ", createdBy='" + createdBy + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", serviceProviders='" + serviceProviders + '\'' +
                ", bank='" + bank + '\'' +
                ", saved='" + saved + '\'' +
                ", savedDescription='" + savedDescription + '\'' +
                ", institutionURL='" + institutionURL + '\'' +
                ", institutionAppKey='" + institutionAppKey + '\'' +
                ", institutionIntegrationVersion='" + institutionIntegrationVersion + '\'' +
                ", globalSetting='" + globalSetting + '\'' +
                ", component1='" + component1 + '\'' +
                ", component2='" + component2 + '\'' +
//                ", isKeyMgt='" + isKeyMgt + '\'' +
                ", isnotification='" + isnotification + '\'' +
                '}';
    }



}
