package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.user.User;
import com.codessquad.qna.web.domain.user.UserRepository;
import com.codessquad.qna.web.dto.user.CreateUserRequest;
import com.codessquad.qna.web.exception.CRUDAuthenticationException;
import com.codessquad.qna.web.exception.EntityNotFoundException;
import com.codessquad.qna.web.exception.FailedLoginException;
import com.codessquad.qna.web.utils.SessionUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String userId, String password, HttpSession session) {

        if(SessionUtils.isLoginUser(session)){
            return SessionUtils.getLoginUser(session);
        }

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("No user with userId " + userId));

        if (!user.isMatchingPassword(password)) {
            throw new FailedLoginException();
        }
        return user;
    }

    public void create(CreateUserRequest request){
        userRepository.save(request.toEntity());
    }

    public List<User> list(){
        return userRepository.findAll();
    }

    public User verifyUser(long id, HttpSession session){
        User user = findUserById(id);
        User loginUser = SessionUtils.getLoginUser(session);

        if (!loginUser.isMatchingWriter(user)) {
            throw new CRUDAuthenticationException("You don't have auth");
        }
        return user;
    }

    public User updateProfile(long id, User updatedUser, String oldPassword){
        User user = findUserById(id);

        if (user.isMatchingPassword(oldPassword)) {
            user.update(updatedUser);
            userRepository.save(user);
        }
        return user;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No user with id number " + id));
    }

}


