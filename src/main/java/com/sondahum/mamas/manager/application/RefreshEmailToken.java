package com.sondahum.mamas.manager.application;

import com.sondahum.mamas.manager.application.port.out.ManagerRepository;
import com.sondahum.mamas.manager.domain.Manager;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class RefreshEmailToken {
    private final ManagerRepository managerRepository;

    public Manager refresh(Manager manager) {
        return managerRepository.save(
                new Manager(
                        manager.id,
                        manager.password,
                        manager.name,
                        manager.email,
                        UUID.randomUUID().toString(),
                        manager.emailVerified,
                        manager.company,
                        manager.createdAt,
                        manager.updatedAt
                ));
    }
}
