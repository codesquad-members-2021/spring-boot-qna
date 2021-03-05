package com.codessquad.qna.web.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping()
    public String createUser(String userId, String password, String name, String email) {
        User createdUser = new User(userId, password, name, email);
        userRepository.save(createdUser);
        return "redirect:/users";
    }

    @GetMapping()
    public String getUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String getOneUser(@PathVariable("userId") long id, Model model) {
        User foundUser = getUserById(id);
        model.addAttribute("user", foundUser);
        return "user/profile";
    }

    @GetMapping("/modify/{userId}")
    public String getModifyUserPage(@PathVariable("userId") long id, Model model) {
        User foundUser = getUserById(id);
        model.addAttribute("user", foundUser);
        return "user/modify-form";
    }

    private User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping("/modify")
    public String modifyUser(long id, String prevPassword, String newPassword,
                             String name, String email) {
        User foundUser = getUserById(id);
        if (foundUser != null && foundUser.getPassword().equals(prevPassword)) {
            foundUser.setPassword(newPassword);
            foundUser.setName(name);
            foundUser.setEmail(email);
            return "redirect:/users/" + foundUser.getUserId();
        }
        return "redirect:/users";
    }
}
