package com.sondahum.mamas.domain.estate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstateRepository extends JpaRepository<Estate, Long> {

    Optional<Estate> findByName(String name);
}
