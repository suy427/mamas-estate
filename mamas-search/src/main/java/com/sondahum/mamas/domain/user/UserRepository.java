package com.sondahum.mamas.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName_AndActive(String name, boolean active);
    Optional<User> findByName_AndPhone_AndActive(String name, String phone, boolean active);
    Optional<User> findByPhone_AndActive(String phone, boolean active);

    @Transactional
    @Modifying
    @Query("UPDATE FROM User u SET u.active=true WHERE u.id=?1")
    Optional<User> deleteByIdInQuery(Long id);
}
