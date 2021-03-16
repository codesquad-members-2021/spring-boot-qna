package com.codessquad.qna.users;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    public Optional<User> findByUserId(String userId);
}
