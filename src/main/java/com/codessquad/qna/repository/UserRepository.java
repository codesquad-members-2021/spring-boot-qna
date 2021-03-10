package com.codessquad.qna.repository;

import com.codessquad.qna.domain.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsUserByUserId(String userId);

    User findUserByUserId(String userId);

    @Override
    List<User> findAll();
}
