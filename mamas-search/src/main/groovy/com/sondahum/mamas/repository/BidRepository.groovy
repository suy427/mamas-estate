package com.sondahum.mamas.repository

import com.sondahum.mamas.domain.Bid
import org.springframework.data.jpa.repository.JpaRepository

interface BidRepository extends JpaRepository<Bid, Long> {
}