package com.codessquad.qna.web.domain.repository;

import com.codessquad.qna.web.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    List<User> findAll();

    Optional<User> findByUserId(String userId);
}
