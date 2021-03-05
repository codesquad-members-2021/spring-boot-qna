package com.codessquad.qna.web;

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

    @PostMapping("/users")
    public String createUser(User user) {
        setIndex(user);
        users.add(user);
        return "redirect:/users";
    }

    private void setIndex(User user) {
        int index = users.size() +1;
        user.setIndex(index);
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String getProfile(@PathVariable String userId, Model model) {
        //id 중복 확인, null일경우 처리
        User foundUser = findByUserId(userId);
        model.addAttribute("user", foundUser);
        return "/user/profile";
    }

    private User findByUserId(String userId) {
        User foundUser = null;
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                foundUser = user;
            }
        }
        return foundUser;
    }

    @GetMapping("/users/{index}/form")
    public String getUpdateForm(@PathVariable int index, Model model){
        model.addAttribute("user", users.get(index-1));
        return "/user/updateForm";
    }

    @PostMapping("/users/{index}/update")
    public String updateUser(User updatedUser){
        users.set(updatedUser.getIndex()-1, updatedUser);
        return  "redirect:/users";
    }
}
