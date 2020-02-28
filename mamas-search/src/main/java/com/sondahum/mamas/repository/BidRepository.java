package com.sondahum.mamas.repository;

import com.sondahum.mamas.domain.bid.Action;
import com.sondahum.mamas.domain.bid.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long>, JpaSpecificationExecutor<Bid> {

    List<Bid> findByAction(Action action);

}
