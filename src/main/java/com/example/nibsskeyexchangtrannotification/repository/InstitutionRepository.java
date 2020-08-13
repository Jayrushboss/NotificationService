package com.example.nibsskeyexchangtrannotification.repository;

import com.example.nibsskeyexchangtrannotification.models.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@EnableJpaRepositories
@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    Institution findByInstitutionID(String institutionID);

}
