package com.sondahum.mamas.dto

import java.time.LocalDate

class BidDto {
    LocalDate createdDate
    EstateDto estate
    String action //  sell/ buy
    String price
}
