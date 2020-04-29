package com.sondahum.mamas.domain.bid;

import com.sondahum.mamas.domain.bid.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    Optional<Bid> findByUser_NameAndEstate_NameAndAction_AndActive(String user, String estate, Action action, boolean active);

    @Transactional
    @Modifying
    @Query("UPDATE FROM bid b SET b.active=true WHERE b.id=?1")
    Optional<Bid> deleteByIdInQuery(Long id);
}
