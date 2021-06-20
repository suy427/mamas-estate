package com.sondahum.mamas.manager.application;

import com.sondahum.mamas.manager.application.port.in.VerifyEmail;
import com.sondahum.mamas.manager.application.port.out.persistence.ManagerRepository;
import com.sondahum.mamas.manager.domain.Manager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VerifyEmailImpl implements VerifyEmail {
    private final ManagerRepository managerRepository;

    @Override
    public void verify(Manager me, String token) {
        if (me.emailToken != token) // todo
            throw new IllegalArgumentException("Failed to verify account - cannot find this account : " + me.id);

        managerRepository.save(
                new Manager(
                        me.id,
                        me.password,
                        me.name,
                        me.email,
                        me.emailToken,
                        true,
                        me.company,
                        me.createdAt,
                        me.updatedAt
                ));
    }
}
