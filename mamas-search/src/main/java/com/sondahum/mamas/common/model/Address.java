package com.sondahum.mamas.common.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Address {

    private String address1;
    private String address2;
    private String address3;

}
