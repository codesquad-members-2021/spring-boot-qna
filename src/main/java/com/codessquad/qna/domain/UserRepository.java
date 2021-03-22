package com.codessquad.qna.domain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    //리턴타입 1 user
    //리턴타입 2 optional
}