package com.codessquad.qna.controller;

import com.codessquad.qna.dto.UserDto;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.UserNotFoundException;
import com.codessquad.qna.service.UserService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * UserDto 로 받아와서 User 객체로 Mapping 해준뒤 해당 유저를 생성합니다.
     * @param userDto
     * @return redirect User list (/users)
     */
    @PostMapping("")
    public String createAccount(@ModelAttribute UserDto userDto) {
        userService.save(userDto);
        logger.info("user: {} ", userDto);
        return "redirect:/users";
    }

    /**
     * 유저가 회원가입할 수 있는 창으로 이동합니다.
     * @return
     */
    @GetMapping("/form")
    public String goUserCreateAccountForm() {
        return "user/form";
    }

    /**
     * 유저 로그인 동작 로직
     * 만약 비밀번호가 일치 하지않는다면 로그인폼으로 redirect 시킨다.
     * @param userId
     * @param password
     * @return
     */
    @PostMapping("/login")
    public String login(String userId, String password, HttpSession httpSession) {
        User user = userService.getUserByUserId(userId);
        logger.info("user : {}", user);
        if(userService.isMatchedUserAndPassword(user, password)) {
            httpSession.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
            return "redirect:/";
        }
        return "redirect:/users/loginForm";
    }

    @GetMapping("/loginForm")
    public String getLoginFormPage() {
        return "user/login";
    }

    /**
     * User 가 logout 한다.
     * Session 에서 sessionUser 의 data 또한 삭제한다.
     * @param httpSession
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    /**
     * 모든 유저 목록을 가져옵니다.
     * @param model
     * @return All User Accounts to List
     */
    @GetMapping("")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "user/list";
    }

    /**
     * 해당 유저의 프로파일로 이동합니다.
     * 만약 해당 유저를 찾을 수 없다면 CanNotFindUserException 을 리턴합니다.
     * @param id userId
     * @param model
     * @throws UserNotFoundException
     * @return only for users with the same id
     */
    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user/profile";
    }

    /**
     * 유저 프로필 수정이 가능한 창으로 이동합니다.
     * 만약 해당 유저를 찾을 수 없다면 CanNotFindUserException 을 리턴합니다.
     * @param id userId
     * @param model
     * @return
     */
    @GetMapping("/{id}/form")
    public String updateUserProfileForm(@PathVariable Long id, Model model, HttpSession httpSession) throws IllegalAccessException {
        User sessionUser = HttpSessionUtils.getUserFromSession(httpSession);
        if(!sessionUser.isMatchedId(id)){
            throw new IllegalAccessException("다른 유저의 프로필을 수정할 수 없습니다.");
        }
        model.addAttribute("user", sessionUser);
        return "user/updateForm";
    }

    /**
     * 유저 프로필을 해당 userDto로 온 값으로 업데이트 합니다.
     * @param id userId
     * @param userDto
     * @return
     */
    @PutMapping("/{id}")
    public String updateUserProfile(@PathVariable Long id, @ModelAttribute UserDto userDto) throws IllegalAccessException {
        userService.change(id, userDto);
        return "redirect:/users";
    }


}
