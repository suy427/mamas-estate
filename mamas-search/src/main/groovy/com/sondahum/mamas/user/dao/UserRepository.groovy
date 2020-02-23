package com.sondahum.mamas.user.dao

import com.sondahum.mamas.user.domain.User
import com.sondahum.mamas.common.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByName(String name)
    List<User> findByRole(Role role)
    User findByPhone(String phone)

    void deleteByName(String name)

}