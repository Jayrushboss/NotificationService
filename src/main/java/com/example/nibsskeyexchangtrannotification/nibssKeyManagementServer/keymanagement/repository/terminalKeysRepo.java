package com.example.nibsskeyexchangtrannotification.nibssKeyManagementServer.keymanagement.repository;

import com.example.nibsskeyexchangtrannotification.nibssKeyManagementServer.keymanagement.model.terminalKeyManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JoshuaO
 */
@EnableJpaRepositories
@Repository
public interface terminalKeysRepo extends JpaRepository<terminalKeyManagement, Long> {
    terminalKeyManagement findByTerminalID(String terminalID);
    @Query(value = "select *\n" +
            "from terminal_key_management where parameter_downloaded is null;",nativeQuery = true)
    List<terminalKeyManagement> findnullparameters();
    @Query(value = "select *\n" +
            "from terminal_key_management where session_key is null;",nativeQuery = true)
    List<terminalKeyManagement> findnullsessionkey();
    @Query(value = "select *\n" +
            "from terminal_key_management where pin_key is null;",nativeQuery = true)
    List<terminalKeyManagement> findnullpinkey();
    @Query(value = "select *\n" +
            "from terminal_key_management where master_key is null;",nativeQuery = true)
    List<terminalKeyManagement> findnullmasterkey();
}
