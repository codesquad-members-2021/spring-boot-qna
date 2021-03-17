package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.UnauthorizedAccessException;

import java.util.List;

public interface UserService {

    List<User> getUserList();

    void register(User user);

    User getUserById(Long id) throws NotFoundException;

    void updateUserInfo(Long id, String oldPassword, User newUserInfo) throws UnauthorizedAccessException;

    User authenticate(String userId, String password) throws UnauthorizedAccessException;

    void checkAccessId(User loginUser, Long accessId);
}
