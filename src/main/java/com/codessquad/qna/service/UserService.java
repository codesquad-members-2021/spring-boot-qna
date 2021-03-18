package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.type.DuplicateIdFoundException;
import com.codessquad.qna.exception.type.IncorrectAccountException;
import com.codessquad.qna.exception.type.UnauthorizedException;
import com.codessquad.qna.repository.UserRepository;
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

    public void save(User user, boolean update){
        User user1 = Optional.ofNullable(user).orElseThrow(IllegalArgumentException::new);
        AtomicBoolean duplicateCheck = new AtomicBoolean(false); //m 멀티스레드 환경에서 경쟁상태가 발생할 수 있으므로, 람다식 내부에서 지역변수(boolean)는 수정이 불가능하다.
        userRepository.findByUserId(user1.getUserId())
                .ifPresent(u -> {
                    duplicateCheck.set(true); // ID 중복 발생
                });
        if(duplicateCheck.get() && !update) {  // 중복이면서, 업데이트가 아닐때 예외발생, AtomicBoolean을 처음써봐서 이런식으로 해도 되는건지 잘 모르겠다.
            throw new DuplicateIdFoundException();
        }else{
            userRepository.save(user1);
        }
    }

    public Iterable<User> getList(){ // ? k는 오버라이드해서 Optional로 바꿔줬던데.. 예외처리 쉽게 하려면 해야할 것 같기도
        return userRepository.findAll();
    }

    public User getById(Long primaryKey){
        if(checkIllegalArgumentOf(primaryKey)) {
            throw new IllegalArgumentException("잘못된 인자입니다.");
        }
        return userRepository.findById(primaryKey).orElseThrow(IncorrectAccountException::new);
    }
    // getById() 오버로딩
    public User getById(String userId){
        if(checkIllegalArgumentOf(userId)) {
            throw new IllegalArgumentException("잘못된 인자입니다.");
        }
        return userRepository.findByUserId(userId).orElseThrow(IncorrectAccountException::new);
    }

    public void authenticateOfId(User selectedUser, User loginUser) { // 네이밍 고민
        User user1 = Optional.ofNullable(selectedUser).orElseThrow(IllegalArgumentException::new);
        User user2 = Optional.ofNullable(loginUser).orElseThrow(IllegalArgumentException::new);

        if(!Objects.equals(user1.getUserId(), user2.getUserId())){
            throw new UnauthorizedException();
        }
    }

    public void authenticateOfPw(User selectedUser, User loginUser) {
        User user1 = Optional.ofNullable(selectedUser).orElseThrow(IllegalArgumentException::new);
        User user2 = Optional.ofNullable(loginUser).orElseThrow(IllegalArgumentException::new);

        if(!Objects.equals(user1.getPassword(), user2.getPassword())){
            throw new UnauthorizedException();
        }
    }

    public void checkValidOfLogin(String userId, String password){
        if(checkIllegalArgumentOf(userId, password)) {
            throw new IllegalArgumentException("잘못된 인자입니다.");
        }
        User findUser = getById(userId);
        if(!Objects.equals(password, findUser.getPassword())){
            throw new IncorrectAccountException();
        }
    }

    public User update(User originUserData, User updateUserData) {
        User originUser = Optional.ofNullable(originUserData).orElseThrow(IllegalArgumentException::new);
        User updateUser = Optional.ofNullable(updateUserData).orElseThrow(IllegalArgumentException::new);

        authenticateOfId(originUser,updateUser);
        authenticateOfPw(originUser,updateUser);

        originUser.setName(updateUser.getName()); // 이 역할을 User 클래스가 해야할지.. 고민
        originUser.setPassword(updateUser.getNewPassword());
        originUser.setEmail(updateUser.getEmail());
        return originUser;
    }

    public static boolean checkIllegalArgumentOf(String str) {
        return (str == null || str.isEmpty());
    }
    // checkIllegalArgumentOf() 오버로딩
    public static boolean checkIllegalArgumentOf(String str, String str2) {
        return (str == null || str.isEmpty()) && (str2 == null || str2.isEmpty()) ;
    }
    // checkIllegalArgumentOf() 오버로딩
    public static boolean checkIllegalArgumentOf(Long l) {
        return l == null;
    }
}
