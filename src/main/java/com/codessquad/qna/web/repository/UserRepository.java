package com.codessquad.qna.web.repository;

import com.codessquad.qna.web.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
    List<User> findAll();
    Optional<User> findByUserId(String userId);
    void update(User originUser, User user);
}
