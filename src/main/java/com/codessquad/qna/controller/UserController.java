package com.codessquad.qna.controller;

import com.codessquad.qna.dto.UserDto;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.CanNotFindUserException;
import com.codessquad.qna.service.UserService;
import com.codessquad.qna.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
        if(!user.isMatchingPassword(password)) {
            logger.info("User password not matched : " + user.toString());
            return "redirect:/user/loginForm";
        }
        httpSession.setAttribute("sessionUser", user);
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
        httpSession.removeAttribute("sessionUser");
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
    public String updateUserProfileForm(@PathVariable Long id, Model model) {
        getUsetIfExist(id, model);
        return "user/updateForm";
    }

    /**
     * 유저 프로필을 해당 userDto로 온 값으로 업데이트 합니다.
     * @param id
     * @param userDto
     * @return
     */
    @PutMapping("/user/{id}")
    public String updateUserProfile(@PathVariable Long id, @ModelAttribute UserDto userDto) {
        User changeUser = Mapper.mapToUser(userDto);
        userService.change(userService.getUserById(id), changeUser);
        return "redirect:/users";
    }

   private void getUsetIfExist(Long id, Model model) {
        try {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
        } catch (CanNotFindUserException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 잘못된 입력값이 들어왔을때 핸들링 해주는 메소드
     * 후에 공부해서 에러페이지를 만들어서 처리해야할듯함
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(Exception e, Model model) {
        handleException(e, model);
        return "error";
    }

    /**
     * 유저를 가져오지 못할시 처리할 로직 생성
     * @return
     */
    @ExceptionHandler(CanNotFindUserException.class)
    public String handlerCanNotFindUserException(Exception e, Model model) {
        handleException(e, model);
        return "error";
    }

    private void handleException(Exception e, Model model) {
        logger.error(e.getMessage());
        model.addAttribute("exception", e);
    }

}
