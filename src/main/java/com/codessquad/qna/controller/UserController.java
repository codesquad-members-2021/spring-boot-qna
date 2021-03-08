package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public String createUser(User user, RedirectAttributes redirect) {
        try {
            userService.join(user);
        } catch (IllegalStateException e) {
            redirect.addFlashAttribute("fail", true);
            return "redirect:/users/form";
        }
        return "redirect:/users";
    }

    @GetMapping("")
    public String userList(Model model) {
        List<User> users = userService.findUserAll();
        model.addAttribute("users", users);
        return "/user/list";
    }


    @GetMapping("/{id}")
    public String userProfile(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateUserForm(@PathVariable String userId, Model model) {
        User user = userService.findUserByUserId(userId);
        model.addAttribute("user", user);
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUserProfile(@PathVariable Long id, User user, RedirectAttributes redirect) {
        User originUser = userService.findById(id);
        String[] passwordArray = user.getPassword().split(",");

        String receivedPassword = passwordArray[0];
        String newPassword = passwordArray[1];

        if (!originUser.isMatchingPassword(receivedPassword)) {
            redirect.addFlashAttribute("fail", true);
            return "redirect:/users/" + originUser.getUserId() + "/form";
        }

        user.setPassword(newPassword);
        userService.updateUserData(originUser, user);
        return "redirect:/users";
    }
}
