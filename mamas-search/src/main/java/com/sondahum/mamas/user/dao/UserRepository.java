package com.sondahum.mamas.user.dao;

import com.sondahum.mamas.common.model.Role;
import com.sondahum.mamas.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);

    List<User> findByRole(Role role);

    Optional<User> findByPhone(String phone);


    void deleteByName(String name);

    /**
     * TODO
     * QueryDSL을 못쓰게 되면서 아래와같은 메소드들이 늘었다.
     * 다행히 JPA에서 따로 구현없이 이런 메소드들을 생성해주지만
     * User의 경우에는 별로 검색조건이 없다쳐도, 나머지 경우에도 이게 괜찮을지는 모르겠다..
     */
    List<User> findByNameLike(String name);
    List<User> findByPhoneLike(String phone);
}
