package com.codessquad.qna.domain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //JpaRepository<User, Long>
    //JpaRepository<저장할 데이터, 프라이머리 키>

}