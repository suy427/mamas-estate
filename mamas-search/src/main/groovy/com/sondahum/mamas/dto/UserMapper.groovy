package com.sondahum.mamas.dto

import com.sondahum.mamas.domain.User
import org.mapstruct.Mapper


@Mapper
interface UserMapper {
    User toEntity(UserDto userDto)
    UserDto toDto(User user)
}
