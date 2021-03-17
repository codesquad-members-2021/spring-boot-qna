package com.codessquad.qna.repository;

import com.codessquad.qna.entity.User;

import java.util.Optional;

public interface UserRepository extends GenericRepository<User, Long> {
    public Optional<User> findByUserId(String userId);
}
