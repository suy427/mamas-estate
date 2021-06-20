package com.sondahum.mamas.manager.application;

import com.sondahum.mamas.manager.application.port.in.FindManager;
import com.sondahum.mamas.manager.domain.Manager;

import java.util.Optional;

public class FindManagerImpl implements FindManager {
    @Override
    public Optional<Manager> byName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Manager> byEmail(String email) {
        return Optional.empty();
    }
}
