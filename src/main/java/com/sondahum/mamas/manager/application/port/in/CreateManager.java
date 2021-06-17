package com.sondahum.mamas.manager.application.port.in;

import com.sondahum.mamas.manager.domain.Manager;
import lombok.RequiredArgsConstructor;

public interface CreateManager {

    Manager create(CreateForm createForm);

    @RequiredArgsConstructor
    final class CreateForm {
        final String name;
        final String email;
        final String password;
    }
}
