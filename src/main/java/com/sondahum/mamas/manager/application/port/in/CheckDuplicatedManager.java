package com.sondahum.mamas.manager.application.port.in;


public interface CheckDuplicatedManager {
    Boolean byName(String name);

    Boolean byEmail(String email);
}
