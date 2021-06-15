package com.sondahum.mamas.security;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Data
@Entity
@Table(name = "persistent_logins")
class RememberMe {
    @Id
    @Column(name = "token")
    public String token;

    @Column(name = "username")
    public String username;

    @Column(name = "series")
    public String series;

    @Column(name = "last_used")
    public Instant lastUsedAt;
}