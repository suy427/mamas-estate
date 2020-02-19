package com.sondahum.mamas.contract.dao

import com.sondahum.mamas.contract.domain.Contract
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContractRepository  extends JpaRepository<Contract, Long> {
}
