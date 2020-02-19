package com.sondahum.mamas.estate.dao

import com.sondahum.mamas.estate.domain.Estate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EstateRepository  extends JpaRepository<Estate, Long> {

}