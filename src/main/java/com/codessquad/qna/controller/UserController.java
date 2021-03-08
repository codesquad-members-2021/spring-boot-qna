package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping
    public String signup(User user) {
        user.setId(users.size()+1);
        users.add(user);
        System.out.println("user : " + user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String viewUserProfile(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
                return "user/profile";
            }
        }
        return "redirect:/users";
    }

    @GetMapping("{id}/form")
    public String viewUpdateUserForm(@PathVariable int id, Model model){
        for(User user : users){
            if(user.getId()==id) {
                model.addAttribute("user", user);
                return "user/updateForm";
            }
        }
        return "redircet:/users";
    }

    @PostMapping("{id}/update")
    public String updateUser(@PathVariable int id, User updateUser){
        User targetUser = users.get(id+1);
        targetUser.set(updateUser);
        return "redirect:/users";
    }
}
