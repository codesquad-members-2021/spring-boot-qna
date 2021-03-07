package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping("/users/create")
    public String create(User user){
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getUserList(Model model){
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String getUserProfile(@PathVariable("userId") String userId, Model model){
        model.addAttribute("user", findUserById(userId));
        return "user/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String getEditProfileForm(@PathVariable("userId") String userId, Model model){
        model.addAttribute("user", findUserById(userId));
        return "user/updateForm";
    }

    @PostMapping("/users/{id}/update")
    public String updateProfile(User updatedUser, String oldPassword){
        User user = findUserById(updatedUser.getUserId());
        if(user.getPassword().equals(oldPassword)){
            user.updateAll(updatedUser);
        }
        return "redirect:/users";
    }


    private User findUserById(String userId){
        for(User user : users){
            if(user.getUserId().equals(userId)){
                return user;
            }
        }
        throw new IllegalArgumentException("There is no user id, " + userId);
    }

}
