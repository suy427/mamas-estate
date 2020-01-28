package com.sondahum.mamas.elasticsearch.dto

import com.sondahum.mamas.common.domain.person.User

class UserDto implements Serializable, EsDto{
    String userId
    User user
}
