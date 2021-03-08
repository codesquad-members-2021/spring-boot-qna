package com.codessquad.qna.repository;

import com.codessquad.qna.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {

    void save(User user);

    List<User> findUserList();

    User findById(String userId);

    User findByName(String name);

}
