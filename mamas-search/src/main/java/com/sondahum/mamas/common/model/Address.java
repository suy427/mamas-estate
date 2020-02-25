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

}
