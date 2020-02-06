package com.sondahum.mamas.repository

import com.sondahum.mamas.domain.Bid
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BidRepository extends JpaRepository<Bid, Long> {
}