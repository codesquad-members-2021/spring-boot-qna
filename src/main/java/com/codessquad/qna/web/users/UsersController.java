package com.codessquad.qna.web.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping()
    public String createUser(User createdUser) {
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

    @PutMapping("/modify")
    public String modifyUser(long id, String userId, String prevPassword, String newPassword,
                             String name, String email) {
        User foundUser = getUserById(id);
        if (foundUser != null && foundUser.getPassword().equals(prevPassword)) {
            if (!prevPassword.equals(newPassword)) {
                foundUser.setPassword(newPassword);
            }
            foundUser.setName(name);
            foundUser.setEmail(email);
            userRepository.save(foundUser);
            return "redirect:/users/" + foundUser.getId();
        }
        return "redirect:/users";
    }
}
