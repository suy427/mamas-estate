package com.sondahum.mamas.manager.domain;


import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class Manager {
    public final String id;
    public final String password;
    public final String name;
    public final String email;
    public final String emailToken;
    public final Boolean emailVerified;
    public final String company;
    public final Instant createdAt;
    public final Instant updatedAt;
}
