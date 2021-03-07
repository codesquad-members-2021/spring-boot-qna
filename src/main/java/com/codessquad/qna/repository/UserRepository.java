package com.codessquad.qna.repository;

import com.codessquad.qna.domain.User;
import java.util.List;

public interface UserRepository {

    User save(User user);

    List<User> findAll();

    User findUserByUserId(String userId);

    boolean existsUserByUserId(String userId);

    User findUserById(Long id);

}
