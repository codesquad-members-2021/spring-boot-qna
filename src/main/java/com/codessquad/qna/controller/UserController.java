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

    UserService userService = new UserService();

    String userIdCheck = null;

    @PostMapping ("/user/create")
    public String create(User user) {

        userService.create(user);

        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String list(Model model){

        model.addAttribute("users", userService.findUsers());
        System.out.println(model);
        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable("userId") String userId, Model model) {

        model.addAttribute("user", userService.findUser(userId));

        return "/user/profile";
    }

    @GetMapping("/user/{userId}/validation")
    public String userValidation(@PathVariable String userId, Model model){

        model.addAttribute("userId", userId);

        userIdCheck = userId;

        return "/user/validation_user";
    }

    @PostMapping("/validation")
    public String validationUser(User user, Model model) {

        if(userService.validationUserInfo(userIdCheck, user.getPassword())){
            model.addAttribute("userId", userIdCheck);
            return "/user/updateForm";
        }
        return "/user/validation_user.html";
    }

    @PostMapping("/user/update")
    public String userUpdate(User user){
        System.out.println(user);

        userService.findUser(user.getUserId()).setPassword(user.getPassword());
        userService.findUser(user.getUserId()).setEmail(user.getEmail());
        userService.findUser(user.getUserId()).setName(user.getName());

        return "redirect:/user/list";
    }

}
