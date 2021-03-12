package com.codessquad.qna.repository;

import com.codessquad.qna.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    //m UserRepository에서 userId에 해당하는 데이터 조회
    User findByUserId(String userId);
}
