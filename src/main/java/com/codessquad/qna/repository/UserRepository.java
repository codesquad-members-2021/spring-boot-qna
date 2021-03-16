package com.codessquad.qna.repository;

import com.codessquad.qna.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    public Optional<User> findByUserId(String userId);
}
