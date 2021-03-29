package com.codessquad.qna.service;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.Result;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NoUserException;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.web.HttpSessionUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean save(String userId, String password, String name, String email) {
        User user = new User(userId, password, name, email);
        if (user.checkEmpty(user)) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    @Transactional
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(NoUserException::new);
    }

    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Transactional
    public void updateUser(long id, String userId, String password, String name, String email, String newPassword) {
        User user = getUserById(id);
        checkPassword(user, password);

        user.update(userId, password, name, email, newPassword);
    }

    private void checkPassword(User originalUser, String password) {
        if (!originalUser.isMatchingPassword(password)) {
            throw new IllegalArgumentException("비밀번호가 맞지않습니다. 비밀번호를 확인해 주세요");
        }
    }

    public Result valid(Long id, boolean isLoginUser, User sessionUser) {
        if (!isLoginUser) {
            return Result.fail("로그인을 먼저 진행해주세요.");
        }

        if (!sessionUser.isMatchingId(id)) {
            return Result.fail("수정할 수 있는 권한이 없습니다.");
        }

        return Result.ok();
    }

    public Result valid(User user, String password) {
        if (!user.isMatchingPassword(password)) {
            return Result.fail("비밀번호를 확인하여 주세요");
        }
        return Result.ok();
    }

}
