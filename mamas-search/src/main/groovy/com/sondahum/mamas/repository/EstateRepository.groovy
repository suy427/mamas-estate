package com.sondahum.mamas.repository

import com.sondahum.mamas.domain.Estate
import org.springframework.data.jpa.repository.JpaRepository

interface EstateRepository  extends JpaRepository<Estate, Long> {

}