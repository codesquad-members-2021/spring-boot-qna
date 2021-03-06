package com.codessquad.qna.repository;

import com.codessquad.qna.domain.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByUserId(String userId);

    Optional<User> findByName(String name);

    List<User> findAll();
}
