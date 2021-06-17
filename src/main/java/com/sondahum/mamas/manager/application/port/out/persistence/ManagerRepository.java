package com.sondahum.mamas.manager.application.port.out.persistence;

import com.sondahum.mamas.manager.domain.Manager;

import java.util.Optional;

public interface ManagerRepository {
    Optional<Manager> findByName(String name);

    Optional<Manager> findByEmail(String email);

    Manager save(Manager manager);
}
