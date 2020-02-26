package com.sondahum.mamas.common.model;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String address1;
    private String address2;
    private String address3;

    @Override
    public String toString() {
        return address1 + " " + address2 + " " + address3;
    }

}
