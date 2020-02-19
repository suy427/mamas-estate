package com.sondahum.mamas.model

import lombok.Getter

import javax.persistence.AttributeOverride
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
@Getter
class Address {
    String address1
    String address2
    String address3
}
