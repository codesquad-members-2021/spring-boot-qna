package com.codessquad.qna.controller;

import com.codessquad.qna.dto.UserDto;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.CanNotFindUserException;
import com.codessquad.qna.service.UserService;
import com.codessquad.qna.util.HttpSessionUtils;
import com.codessquad.qna.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    /**
     * UserDto 로 받아와서 User 객체로 Mapping 해준뒤 해당 유저를 생성합니다.
     * @param userDto
     * @return redirect User list (/users)
     */
    @PostMapping("/user")
    public String createAccount(@ModelAttribute UserDto userDto) {
        User user = Mapper.mapToUser(userDto);
        userService.save(user);
        logger.info(user.toString());
        return "redirect:/users";
    }

    /**
     * 유저 로그인 동작 로직
     * 만약 비밀번호가 일치 하지않는다면 로그인폼으로 redirect 시킨다.
     * @param userId
     * @param password
     * @return
     */
    @PostMapping("/user/login")
    public String login(String userId, String password, HttpSession httpSession) {
        User user = userService.getUserByUserId(userId);
        if(!user.isMatchedPassword(password)) {
            logger.info("User password not matched : " + user.toString());
            return "redirect:/user/loginForm";
        }
        httpSession.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        logger.info("Success User login : " + user.toString());
        return "redirect:/";
    }

    /**
     * User 가 logout 한다.
     * Session 에서 sessionUser 의 data 또한 삭제한다.
     * @param httpSession
     * @return
     */
    @GetMapping("/user/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    /**
     * @param model
     * @return All User Accounts to List
     */
    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "user/list";
    }

    /**
     * 해당 유저의 프로파일로 이동합니다.
     * 만약 해당 유저를 찾을 수 없다면 CanNotFindUserException 을 리턴합니다.
     * @param id
     * @param model
     * @throws CanNotFindUserException
     * @return only for users with the same id
     */
    @GetMapping("/user/{id}")
    public String getUserProfile(@PathVariable Long id, Model model) {
        getUsetIfExist(id, model);
        return "user/profile";
    }

    /**
     * 유저 프로필 수정이 가능한 창으로 이동합니다.
     * 만약 해당 유저를 찾을 수 없다면 CanNotFindUserException 을 리턴합니다.
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/user/{id}/form")
    public String updateUserProfileForm(@PathVariable Long id, Model model, HttpSession httpSession) {
        Optional<User> sessionUser = isMatchedSessionUserById(id, httpSession);
        if (sessionUser.isPresent()) {
            model.addAttribute("user", sessionUser);
            return "user/updateForm";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * 유저 프로필을 해당 userDto로 온 값으로 업데이트 합니다.
     * @param id
     * @param userDto
     * @return
     */
    @PutMapping("/user/{id}")
    public String updateUserProfile(@PathVariable Long id, @ModelAttribute UserDto userDto, HttpSession httpSession) {
        Optional<User> sessionUser = isMatchedSessionUserById(id, httpSession);
        if (sessionUser.isPresent()) {
            User changeUser = Mapper.mapToUser(userDto);
            userService.change(userService.getUserById(id), changeUser);
            return "redirect:/users";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * HttpSession 상의 유저와 url 에 주어진 id 로 mapping 된 유저가 같은 유저인지 테스트 하는 메소드
     * @param id
     * @param httpSession
     * if Matched Session User and User from id
     * @return Optional<User>
     *     else
     * @return Optional.empty();
     */
    private Optional<User> isMatchedSessionUserById(Long id, HttpSession httpSession) {
        if(HttpSessionUtils.isLoggedUser(httpSession)) {
            logger.error("session 에 User 정보가 없습니다.");
        }
        Optional<User> userFromSession = HttpSessionUtils.getUserFromSession(httpSession);
        if(userFromSession.isPresent()){
            User sessionUser = userFromSession.get();
            if(!sessionUser.isMatchedId(id)){
                logger.error("sessionId : " + sessionUser.getId() + " 와 userId : + " + id + " 가 다릅니다. ");
                throw new IllegalArgumentException("자신의 정보만 수정할 수 있습니다.");
            }
            return Optional.of(sessionUser);
        }
        return Optional.empty();
    }

   private void getUsetIfExist(Long id, Model model) {
        try {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
        } catch (CanNotFindUserException e) {
            logger.error(e.getMessage());
        }
    }

}
