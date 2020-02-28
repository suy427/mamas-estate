package com.sondahum.mamas.repository;

import com.sondahum.mamas.domain.estate.Estate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstateRepository extends JpaRepository<Estate, Long>, JpaSpecificationExecutor<Estate> {

    Optional<Estate> findByName(String name);
}
