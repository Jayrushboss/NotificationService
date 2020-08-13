package com.example.nibsskeyexchangtrannotification.repository;

import com.example.nibsskeyexchangtrannotification.models.Terminals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface TerminalRepository extends JpaRepository<Terminals, Long> {
    Terminals findByTerminalID(String terminalID);

    List<Terminals> findByIsNibssKeyMgt(Boolean value);

}

