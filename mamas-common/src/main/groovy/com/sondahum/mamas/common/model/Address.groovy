package com.sondahum.mamas.common.model;

class Address {
    String zipCode

    String state
    String city
    String gu
    String dong
    String detail
    String bunji

    String gil
    String buildingNumber

    // 구 주소 : 도 -> 시 -> 구 -> 동 -> 상세주소 -> 번지
    // 신 주소 : 도 -> 시 -> 구 -> 동 -> 길번호 -> 건물 번호 -> 상세주소
}
