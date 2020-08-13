package com.example.nibsskeyexchangtrannotification.repository;

import com.example.nibsskeyexchangtrannotification.models.keyGen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

/**
 * @author JoshuaO
 */
@EnableJpaRepositories
@Repository
public interface keygenRepository extends JpaRepository<keyGen, Long> {
    keyGen findByTerminalID(String terminalID);
}
