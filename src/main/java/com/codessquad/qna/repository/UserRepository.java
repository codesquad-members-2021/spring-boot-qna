package com.codessquad.qna.repository;

import com.codessquad.qna.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUserId(String id);

    Optional<User> deleteUserById(Long id);

    Optional<User> deleteUserByUserId(String id);

    List<User> findAll();

}
