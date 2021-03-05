package com.codessquad.qna.web.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UsersController {

    private final List<User> userList = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @PostMapping("users")
    public String createUser(String userId, String password, String name, String email) {
        User createdUser = new User(userId, password, name, email);
        userRepository.save(createdUser);
        return "redirect:/users";
    }

    @GetMapping("users")
    public String getUserList(Model model) {
        model.addAttribute("users", userList);
        return "user/list";
    }

    @GetMapping("users/{userId}")
    public String getOneUser(@PathVariable("userId") String userId, Model model) {
        User foundUser = getUserById(userId);
        model.addAttribute("user", foundUser);
        return "user/profile";
    }

    @GetMapping("users/modify/{userId}")
    public String getModifyUserPage(@PathVariable("userId") String userId, Model model) {
        User foundUser = getUserById(userId);
        model.addAttribute("user", foundUser);
        return "user/modify-form";
    }

    private User getUserById(String userId) {
        return userList.stream()
                .filter((user -> user.getUserId().equals(userId)))
                .findFirst().orElse(null);
    }

    @PostMapping("users/modify")
    public String modifyUser(String userId, String prevPassword, String newPassword,
                             String name, String email) {
        User foundUser = getUserById(userId);
        if (foundUser != null && foundUser.getPassword().equals(prevPassword)) {
            foundUser.setPassword(newPassword);
            foundUser.setName(name);
            foundUser.setEmail(email);
            return "redirect:/users/" + foundUser.getUserId();
        }
        return "redirect:/users";
    }
}
