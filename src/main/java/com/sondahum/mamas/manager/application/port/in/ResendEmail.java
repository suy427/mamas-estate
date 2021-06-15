package com.sondahum.mamas.manager.application.port.in;

import com.sondahum.mamas.manager.domain.Manager;

public interface ResendEmail {
    void send(Manager manager);
}
