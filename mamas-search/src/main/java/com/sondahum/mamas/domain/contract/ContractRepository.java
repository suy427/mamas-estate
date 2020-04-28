package com.sondahum.mamas.domain.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    // 와 이런거까지 된다고...???? 그러면 다시 QueryDSL 무용론...?
    Optional<Contract> findBySeller_NameAndBuyer_NameAndEstate_Name_AndValidity(String seller, String buyer, String estate, boolean validity);
}
