package com.codessquad.qna.users;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> getUsers();

    void addUser(User user);

    void updateUser(User user);

    Optional<User> getUser(String userId);
}
