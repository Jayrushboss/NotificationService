package com.example.nibsskeyexchangtrannotification.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "terminals")
public class Terminals {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//Generates the ID for us
    private Long id;
    private String terminalID;
    private String TerminalType;
    private String TerminalSerialNo;
    private String TerminalROMVersion;
    private String dateCreated;
    private String savedDescription;
    @JsonIgnore
    private boolean isSaved;

    @OneToOne
    private profiles profile;
    @ManyToOne
    private Institution institution;

    private Boolean isNibssKeyMgt = true;//they send 0800 message

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", terminalID='" + terminalID + '\'' +
                ", TerminalType='" + TerminalType + '\'' +
                ", TerminalSerialNo='" + TerminalSerialNo + '\'' +
                ", TerminalROMVersion='" + TerminalROMVersion + '\'' +
                ", dateCreated=" + dateCreated +
                ", savedDescription='" + savedDescription + '\'' +
                ", isSaved='" + isSaved + '\'' +
                ", profile='" + profile + '\'' +
                ", institution='" + institution + '\'' +
                ", isKeyMgt='" + isNibssKeyMgt + '\'' +
                '}';
    }
}
