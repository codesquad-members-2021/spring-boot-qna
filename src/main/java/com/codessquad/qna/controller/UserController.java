package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/users")
@Controller
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private List<User> users = new ArrayList<>();

    @PostMapping("/signUp")
    public String signUp(User user) {
        user.setId(users.size() + 1);
        users.add(user);
        logger.info(user.toString());

        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/users/list";
    }

    @GetMapping("/profile/{userId}")
    public String getProfile(@PathVariable("userId") String userId, Model model) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
                return "/users/profile";
            }
        }
        return "redirect:/users/list";
    }

    @GetMapping("/{id}/updateForm")
    public String getUpdateForm(@PathVariable("id") long id, Model model) {
        User user = users.get((int) id - 1);
        model.addAttribute("user", user);
        return "/users/updateForm";
    }

    @PostMapping("/{id}/update")
    public String updateUser(
            @PathVariable("id") long id, User userWithUpdatedInfo, String currentPassword) {
        User targetUser = users.get((int) id - 1);
        if (!targetUser.isCorrectPassword(currentPassword)) {
            logger.warn("비밀번호가 일치하지 않습니다.");
            return "redirect:/users/list";
        }
        logger.info(userWithUpdatedInfo.toString());
        users.set((int) id - 1, userWithUpdatedInfo);
        return "redirect:/users/list";
    }
}
