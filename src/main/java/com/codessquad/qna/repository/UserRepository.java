package com.codessquad.qna.repository;

import com.codessquad.qna.domain.User;
import java.util.List;

public interface UserRepository {

    void save(User user);

    List<User> findUserALl();

    User findUserByUserID(String userId);

    void updateUserData(User user);
}
