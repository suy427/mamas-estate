package com.sondahum.mamas.repository

import com.sondahum.mamas.domain.Contract
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContractRepository  extends JpaRepository<Contract, Long> {
}
