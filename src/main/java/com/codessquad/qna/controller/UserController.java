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

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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

    @GetMapping("/users/{id}")
    public String profile(@PathVariable("userId") Long id, Model model) {

        model.addAttribute("user", userService.findUser(id));

        return "/user/profile";
    }

    @GetMapping("/users/{id}/validation")
    public String userValidation(@PathVariable Long id, Model model){

        model.addAttribute("id", id);

        return "/user/validation_user";
    }

    @PostMapping("/validation")
    public String validationUser(User user, Model model) {

        System.out.println("validationUser : " + user);

        if(userService.validationUserInfo(user.getId(), user.getPassword())){

            model.addAttribute("user", user);

            return "/user/updateForm";
        }

        return "/user/validation_user";
    }

    @PostMapping("/users/{id}")
    public String userUpdate(@PathVariable Long id, User newUser){

        User user = userService.findUser(id).get();

        user.update(newUser);

        userService.create(user);

        return "redirect:/users/list";
    }

}
