package com.example.nibsskeyexchangtrannotification.repository;

import com.example.nibsskeyexchangtrannotification.models.bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface bankServiceRepo extends JpaRepository<bank, Long> {
    bank findByCbnCode(String bankcode);
}
