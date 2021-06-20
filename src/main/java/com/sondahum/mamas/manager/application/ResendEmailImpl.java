package com.sondahum.mamas.manager.application;

import com.sondahum.mamas.manager.application.port.in.ResendEmail;
import com.sondahum.mamas.manager.application.port.out.email.SendEmail;
import com.sondahum.mamas.manager.domain.Manager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResendEmailImpl implements ResendEmail {
    private final RefreshEmailToken refreshEmailToken;
    private final SendEmail sendEmail;

    @Override
    public void send(Manager manager) {
        Manager tokenUpdatedManager = refreshEmailToken.refresh(manager);
        sendEmail.send(tokenUpdatedManager.email, tokenUpdatedManager.emailToken);
    }
}
