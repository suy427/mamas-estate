package com.sondahum.mamas.repository

import com.sondahum.mamas.domain.Estate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EstateRepository  extends JpaRepository<Estate, Long> {

}