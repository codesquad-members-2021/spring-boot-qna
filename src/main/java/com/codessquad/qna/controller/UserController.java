package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/{id}/validation")
    public String userValidation(@PathVariable Long id, Model model){

        model.addAttribute("id", id);

        return "/user/validationUser";
    }

    @PostMapping("/validation")
    public String validationUser(User user, Model model) {


        if(userService.validationUserInfo(user.getId(), user.getPassword())){

            model.addAttribute("user", user);

            return "/user/updateForm";
        }

        return "/user/validationUser";
    }

    @PostMapping("/{id}")
    public String userUpdate(@PathVariable Long id, User newUser){

        User user = userService.findUser(id).get();

        user.update(newUser);

        userService.create(user);

        return "redirect:/users/list";
    }

}
