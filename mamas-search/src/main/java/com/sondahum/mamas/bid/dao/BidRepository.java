package com.sondahum.mamas.bid.dao;

import com.sondahum.mamas.bid.domain.Action;
import com.sondahum.mamas.bid.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    List<Bid> findByAction(Action action);

}
