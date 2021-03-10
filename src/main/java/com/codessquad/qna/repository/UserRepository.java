package com.codessquad.qna.repository;

import com.codessquad.qna.domain.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    List<User> findUserList();

    Optional<User> findById(String userId);

    Optional<User> findByName(String name);

}
