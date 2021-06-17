package com.sondahum.mamas.manager.application.port.in;

import com.sondahum.mamas.manager.domain.Manager;

public interface VerifyEmail {
    void verify(Manager me, String token);
}
