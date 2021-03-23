package com.codessquad.qna.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
    //Optional<User> findByUserId(String userId);
    //리턴타입 1 user
    //리턴타입 2 optional
}