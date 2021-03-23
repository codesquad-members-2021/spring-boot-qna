package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.type.DuplicateException;
import com.codessquad.qna.exception.type.IncorrectAccountException;
import com.codessquad.qna.exception.type.NotFoundException;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by 68936@naver.com on 2021-03-17 오후 4:24
 * Blog : https://velog.io/@san
 * Github : https://github.com/sanhee
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        user = Optional.ofNullable(user).orElseThrow(IllegalArgumentException::new);
        duplicateCheck(user);
        userRepository.save(user);
    }

    public void duplicateCheck(User user) {
        AtomicBoolean duplicateCheck = new AtomicBoolean(false); //m 멀티스레드 환경에서 경쟁상태가 발생할 수 있으므로, 람다식 내부에서 지역변수(boolean)는 수정이 불가능하다.
        userRepository.findByUserId(user.getUserId())
                .ifPresent(u -> {
                    duplicateCheck.set(true); // ID 중복 발생
                });
        if (duplicateCheck.get()) {  // 중복일때 예외발생, AtomicBoolean을 처음써봐서 이런식으로 해도 되는건지 잘 모르겠다.
            throw new DuplicateException();
        }
    }

    public Iterable<User> getList() { // ? k는 오버라이드해서 Optional로 바꿔줬던데.. 예외처리 쉽게 하려면 해야할 것 같기도
        return userRepository.findAll();
    }

    public User getById(Long primaryKey) {
        ValidUtils.checkIllegalArgumentOf(primaryKey);
        return userRepository.findById(primaryKey).orElseThrow(NotFoundException::new);
    }

    // getById() 오버로딩
    public User getById(String userId) {
        ValidUtils.checkIllegalArgumentOf(userId);
        return userRepository.findByUserId(userId).orElseThrow(NotFoundException::new);
    }

    public void authenticateOfId(User selectedUser, User loginUser) {
        if (!Objects.equals(selectedUser.getUserId(), loginUser.getUserId())) {
            throw new IncorrectAccountException("타인의 정보는 열람할 수 없습니다.");
        }
    }

    public void authenticateOfPw(User selectedUser, User loginUser) {
        selectedUser = Optional.ofNullable(selectedUser).orElseThrow(IllegalArgumentException::new);
        loginUser = Optional.ofNullable(loginUser).orElseThrow(IllegalArgumentException::new);

        if (!Objects.equals(selectedUser.getPassword(), loginUser.getPassword())) {
            throw new IncorrectAccountException("비밀번호가 잘못됐습니다.");
        }
    }

    public void checkValidOfLogin(String userId, String password) {
        ValidUtils.checkIllegalArgumentOf(userId, password);
        User findUser = getById(userId);
        if (!Objects.equals(password, findUser.getPassword())) {
            throw new IncorrectAccountException("loginFail");
        }
    }

    public void update(Long primaryKey,User updateUserData) {
        ValidUtils.checkIllegalArgumentOf(primaryKey);

        User originUserData = getById(primaryKey);
        originUserData = Optional.ofNullable(originUserData).orElseThrow(IllegalArgumentException::new);

        updateUserData.setPrimaryKey(primaryKey);
        authenticateOfId(originUserData, updateUserData);
        authenticateOfPw(originUserData, updateUserData);

        originUserData.setName(updateUserData.getName());
        originUserData.setPassword(updateUserData.getNewPassword());
        originUserData.setEmail(updateUserData.getEmail());

        userRepository.save(originUserData);
    }
}
