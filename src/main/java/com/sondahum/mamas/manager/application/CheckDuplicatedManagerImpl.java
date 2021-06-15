package com.sondahum.mamas.manager.application;

import com.sondahum.mamas.manager.application.port.in.CheckDuplicatedManager;
import com.sondahum.mamas.manager.application.port.out.ManagerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckDuplicatedManagerImpl implements CheckDuplicatedManager {
    private final ManagerRepository managerRepository;


    @Override
    public Boolean byName(String name) {
        return managerRepository.findByName(name).isPresent();
    }

    @Override
    public Boolean byEmail(String email) {
        return managerRepository.findByEmail(email).isPresent();
    }
}
