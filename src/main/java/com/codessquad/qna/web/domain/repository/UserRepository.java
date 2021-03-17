package com.codessquad.qna.web.domain.repository;

import com.codessquad.qna.web.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    List<User> findAll();

    User findByUserId(String userId);
}
