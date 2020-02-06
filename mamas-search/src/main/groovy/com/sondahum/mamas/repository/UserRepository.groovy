package com.sondahum.mamas.repository

import com.sondahum.mamas.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository  extends JpaRepository<User, Long> {

}