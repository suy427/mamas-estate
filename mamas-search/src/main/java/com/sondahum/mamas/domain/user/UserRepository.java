package com.sondahum.mamas.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName_AndActiveTrue(String name);
    Optional<User> findByName_AndPhone_AndActiveTrue(String name, String phone);
    Optional<User> findByPhone_AndActiveTrue(String phone);
}
