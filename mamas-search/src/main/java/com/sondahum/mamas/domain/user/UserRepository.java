package com.sondahum.mamas.domain.user;

import com.sondahum.mamas.domain.bid.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName_AndActive(String name, boolean validity);
    Optional<User> findByPhone_AndActive(String phone, boolean validity);

    @Transactional
    @Modifying
    @Query("UPDATE FROM user u SET u.active=true WHERE u.id=?1")
    Optional<User> deleteByIdInQuery(Long id);
}
