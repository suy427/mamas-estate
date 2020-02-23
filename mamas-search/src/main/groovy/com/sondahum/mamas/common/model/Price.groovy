package com.sondahum.mamas.common.model

import javax.persistence.Embeddable

@Embeddable
class Price {
    Long minimum
    Long maximum
}
