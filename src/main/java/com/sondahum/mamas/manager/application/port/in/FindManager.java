package com.sondahum.mamas.manager.application.port.in;

import com.sondahum.mamas.manager.domain.Manager;

import java.util.Optional;


public interface FindManager {
    Optional<Manager> byName(String name);

    Optional<Manager> byEmail(String email);
}
