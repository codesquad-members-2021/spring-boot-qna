package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.HttpSessionUtils;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/loginForm")
    public String getLoginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(User user, HttpSession session) {
        User originUser;
        try {
            originUser = userService.login(user);
        } catch (IllegalStateException e){
            return "redirect:/users/loginForm";
        }
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, originUser);
        return "redirect:/";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    @PostMapping
    public String createUser(User user) {
        try {
            userService.signUp(user);
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
        return "redirect:/users";
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userService.findUsers());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String getProfile(@PathVariable long id, Model model) {
        try {
            model.addAttribute("user", userService.findUser(id));
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getSessionedUser(session);
        if(id != sessionedUser.getId()) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다");
        }

        //여기 findUser 부분 sessionedUseer로 바꿀 것
        try {
            model.addAttribute("user", userService.findUser(id));
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
        return "/user/updateForm";
    }

    //testassword 자체를 넘겨줄 수도 있고, 이 위에서 먼저 확인한다음에 updateUser 할 수도 있겠다 == questionController
    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, String testPassword, User user, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getSessionedUser(session);
        if(id != sessionedUser.getId()) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다");
        }
        //여기 findUser 부분 sessionedUseer로 바꿀 것
        try {
            userService.updateUser(id, testPassword, user);
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
        return "redirect:/users";
    }
}
