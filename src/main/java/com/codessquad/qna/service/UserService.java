package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

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

    public void register(User user){
        userRepository.findByUserId(user.getUserId())
                .ifPresent(u -> {
                    //throw new UserExistException();
                });
        userRepository.save(user);
    }

    public Iterable<User> getList(){ // ??
        return userRepository.findAll();
    }

    public User getById(Long primaryKey){
        return userRepository.findById(primaryKey).orElseThrow(NoSuchElementException::new);
        //m 예외처리 홈페이지 만들어줘야함.
    }
    // 오버로딩
    public User getById(String userId){
        return userRepository.findByUserId(userId).orElseThrow(NoSuchElementException::new);
    }

    public boolean matchingId(User selectedUser, User loginUser) { // 네이밍 고민
        return Objects.equals(selectedUser.getUserId(), loginUser.getUserId());
    }

    public boolean matchingPw(User selectedUser, User loginUser) {
        return Objects.equals(selectedUser.getPassword(), loginUser.getPassword());
    }

    public boolean checkValidOfLogin(String userId, String password){
        User findUser = getById(userId);
        return Objects.equals(password, findUser.getPassword());
    }

    public User update(User originUserData, User updateUserData) {
        if (!matchingId(originUserData,updateUserData) || !matchingPw(originUserData,updateUserData)){
            return originUserData;
        }
        originUserData.setName(updateUserData.getName());
        originUserData.setPassword(updateUserData.getNewPassword());
        originUserData.setEmail(updateUserData.getEmail());
        return originUserData;
    }
}
