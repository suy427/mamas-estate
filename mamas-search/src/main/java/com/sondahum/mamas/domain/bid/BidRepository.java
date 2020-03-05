package com.sondahum.mamas.domain.bid;

import com.sondahum.mamas.domain.bid.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    Optional<Bid> findByUser_NameAndEstate_NameAndAction(String user, String estate, Action action);
    List<Bid> findByAction(Action action);
    List<Bid> findAllWithFetch();
}
