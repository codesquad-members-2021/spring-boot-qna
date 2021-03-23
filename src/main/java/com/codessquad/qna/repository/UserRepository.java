package com.codessquad.qna.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserId(String userId);
    List<User> findAll();
}
