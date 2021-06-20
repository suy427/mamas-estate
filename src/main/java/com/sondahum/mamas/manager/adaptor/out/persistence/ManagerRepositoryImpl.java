package com.sondahum.mamas.manager.adaptor.out.persistence;

import com.sondahum.mamas.manager.application.port.out.persistence.ManagerRepository;
import com.sondahum.mamas.manager.domain.Manager;

import java.util.Optional;

public class ManagerRepositoryImpl implements ManagerRepository {
    @Override
    public Optional<Manager> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Manager> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Manager save(Manager manager) {
        return null;
    }
}
