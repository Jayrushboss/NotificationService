package com.example.nibsskeyexchangtrannotification.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.*;

/**
 * @author JoshuaO
 */

@Entity
@Data
public class serviceProviders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String providerName;
    @OneToMany(cascade={PERSIST, MERGE, REMOVE, REFRESH, DETACH})
    private List<profiles> Profile;
    private boolean isSaved = false;
    private String savedDescription;

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", providerName='" + providerName + '\'' +
                ", Profile='" + Profile + '\'' +
                ", isSaved='" + isSaved + '\'' +
                ", savedDescription='" + savedDescription + '\'' +
                '}';
    }
}
