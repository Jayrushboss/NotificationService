package com.example.nibsskeyexchangtrannotification.models;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;


@ToString
@Entity
@Data
public class bank {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bankName;
    private String bankCode;
    private String cbnCode;
    private String comissionPercentage ;
    private String acquirePercentage;
    @Lob
    private String parameter;

//    private Boolean isIntegrated;


}
