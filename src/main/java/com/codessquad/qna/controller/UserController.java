package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {

    private UserService userService = new UserService();

    @PostMapping ("/users/create")
    public String create(User user) {

        userService.create(user);

        return "redirect:/users/list";
    }

    @GetMapping("/users/list")
    public String list(Model model){

        model.addAttribute("users", userService.findUsers());

        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable("userId") String userId, Model model) {

        model.addAttribute("user", userService.findUser(userId));

        return "/user/profile";
    }

    @GetMapping("/users/{userId}/validation")
    public String userValidation(@PathVariable String userId, Model model){

        model.addAttribute("userId", userId);

        return "/user/validation_user";
    }

    @PostMapping("/validation")
    public String validationUser(User user, Model model) {

        if(userService.validationUserInfo(user.getUserId(), user.getPassword())){
            model.addAttribute("user", user);
            return "/user/updateForm";
        }
        return "/user/validation_user";
    }

    @PostMapping("/users/update")
    public String userUpdate(User user){

        userService.findUser(user.getUserId()).setPassword(user.getPassword());
        userService.findUser(user.getUserId()).setEmail(user.getEmail());
        userService.findUser(user.getUserId()).setName(user.getName());

        return "redirect:/users/list";
    }

}
