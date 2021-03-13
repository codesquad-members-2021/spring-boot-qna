package com.codessquad.qna.user;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();

    UserDTO getUser(Long id);

    UserDTO getUserWithVerifyPassword(String userId, String password);

    void createUser(UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO newUser);
}
