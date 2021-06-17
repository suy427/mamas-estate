package com.sondahum.mamas.manager.adaptor.out.persistence;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "manager")
public class Manager {
    @Id @GeneratedValue
    public Long id;

    @Column
    public String name;
    @Column
    public String phone;

    @Column
    public String email;

    @Column
    public String password;

    @Column
    public String emailToken;

    @Column
    public Boolean emailVerified;

    @Column
    public Instant createdAt;

    @Column
    public Instant updatedAt;
}
