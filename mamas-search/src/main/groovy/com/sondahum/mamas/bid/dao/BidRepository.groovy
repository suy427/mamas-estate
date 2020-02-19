package com.sondahum.mamas.bid.dao

import com.sondahum.mamas.bid.domain.Bid
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BidRepository extends JpaRepository<Bid, Long> {

}