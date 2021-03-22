package com.codessquad.qna.controller;

import com.codessquad.qna.HttpSessionUtils;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping ("/create")
    public String create(User user) {

        userService.create(user);

        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String list(Model model){

        model.addAttribute("users", userService.findUsers());

        return "/user/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable("userId") Long id, Model model) {

        model.addAttribute("user", userService.findUser(id));

        return "/user/profile";
    }

    @GetMapping("/loginForm")
    public String loginForm() {

        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session){

        User user = userService.findById(userId);

        if(user == null){

            return "redirect:/users/loginForm";
        }

        if(!user.matchPassword(password)){

            return "redirect:/users/loginForm";
        }

        HttpSessionUtils.setUserSessionKey(session, user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.removeAttribute("sessionedUser");

        return "redirect:/";
    }

    @GetMapping("/{id}/validation")
    public String userValidation(@PathVariable Long id, Model model, HttpSession session){

        User sessionedUser = (User)session.getAttribute("sessionedUser");

        if(!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/form";
        }

        if(!sessionedUser.matchId(id)) {
            return "redirect:/users/form";
        }

        model.addAttribute("id", id);

        return "/user/validationUser";
    }

    @PostMapping("/validation")
    public String validationUser(User user, Model model, HttpSession session) {
        System.out.println("0");
        if(!HttpSessionUtils.isLoginUser(session)) {
            System.out.println("1");
            return "redirect:/users/form";
        }

        if(userService.validationUserInfo(user, session)){//세션값이랑 입력받은 값 같으면 업데이트
            System.out.println("2");
            model.addAttribute("user", user);

            return "/user/updateForm";
        }
        System.out.println("3");
        return "/user/validationUser";
    }

    @PostMapping("/{id}")
    public String userUpdate(@PathVariable Long id, User newUser, HttpSession session){

        User sessionedUser = (User)session.getAttribute("sessionedUser");

        if(!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/form";
        }

        if(!sessionedUser.matchId(id)) {
            return "redirect:/users/form";
        }

        User user = userService.findUser(id).get();

        user.update(newUser);

        userService.create(user);

        return "redirect:/users/list";
    }

}
