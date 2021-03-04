package com.codessquad.qna.users;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final List<User> users = Collections.synchronizedList(new ArrayList<>());

    @GetMapping()
    String getUsers(Model model) {
        model.addAttribute("users", users);
        return "users/list";
    }

    @PostMapping()
    String createUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
        }
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    String getProfile(@PathVariable String userId, Model model) {
        Optional<User> user = findUser(userId);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "users/profile";
        }
        return "redirect:/users";
    }

    private Optional<User> findUser(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
