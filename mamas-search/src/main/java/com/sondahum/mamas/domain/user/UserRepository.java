package com.sondahum.mamas.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName_AndValidity(String name, boolean validity);
    Optional<User> findByPhone_AndValidity(String phone, boolean validity);
}
