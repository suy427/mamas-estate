package com.sondahum.mamas.model

import lombok.Getter

import javax.persistence.AttributeOverride
import javax.persistence.AttributeOverrides
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
@Getter
class Price {
    Long minimum
    Long maximum
}
