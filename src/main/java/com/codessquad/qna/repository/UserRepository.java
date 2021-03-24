package com.codessquad.qna.repository;

import com.codessquad.qna.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    //m UserRepository에서 userId에 해당하는 데이터 조회
    Optional<User> findByUserId(String userId);
}
