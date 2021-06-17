package com.sondahum.mamas.manager.application.port.out.email;

public interface SendEmail {
    void send(String email, String emailToken);
}
