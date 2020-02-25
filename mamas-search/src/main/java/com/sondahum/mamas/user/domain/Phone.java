package com.sondahum.mamas.user.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Getter
@Builder
@Embeddable
public class Phone {

    @Transient
    private String first;

    @Transient
    private String middle;

    @Transient
    private String last;

    @Column(name = "phone_number")
    private String whole;

    public Phone() {
        whole = first+middle+last;
    }
}
