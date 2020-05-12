package com.sondahum.mamas.domain.estate;

import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.estate.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstateRepository extends JpaRepository<Estate, Long> {
    Optional<Estate> findByNameAndAddress_AndActiveTrue(String name, Address address);
    Optional<Estate> findByName_AndActiveTrue(String name);
}
