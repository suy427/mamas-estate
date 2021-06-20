package com.sondahum.mamas.manager.adaptor.out.persistence;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerJpaRepository {
    Optional<Manager> findByName_AndActiveTrue(String name);

    Optional<Manager> findByName_AndPhone_AndActiveTrue(String name, String phone);

    Optional<Manager> findByPhone_AndActiveTrue(String phone);
}
