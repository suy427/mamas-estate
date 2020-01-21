package com.sondahum.mamas.elasticsearch.dto

class UserDto extends EsDto implements Serializable {

    String name
    String role // 이걸 '소속'이라 생각해도 좋을듯.
    String phone
    String bid
}
