package com.sondahum.mamas.domain.estate;

import com.sondahum.mamas.domain.estate.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstateRepository extends JpaRepository<Estate, Long> {
    Optional<Estate> findByNameAndAddress(String name, Address address);
    Optional<Estate> findByName(String name);
}
