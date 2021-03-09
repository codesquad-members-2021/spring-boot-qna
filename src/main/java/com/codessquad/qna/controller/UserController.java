package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping
    public String createUser(User newUser) {

        if (isRedundant(newUser)) {
            return "redirect:/users";
        }

        if(!userRepository.save(newUser)){
            return "users/form";
        }
        return "redirect:/users";
    }

    // TODO: userRepository.isRedundant 다른 걸로 대체할 수 있을 거 같아
   private boolean isRedundant(User user) {
        return userRepository.isRedundant(user.getUserId());
    }

    @GetMapping
    public String showUsers(Model model) {
        model.addAttribute("users", userRepository.getAll());

        return "/users/list";
    }

    @GetMapping("/{userId}")
    public String showUserInDetail(@PathVariable(name = "userId") String targetId, Model model) {
        model.addAttribute("user", userRepository.getOne(targetId));

        return "/users/profile";
    }

    @GetMapping("/{userId}/form")
    public String passUserId(@PathVariable(name = "userId") String targetId, Model model) {
        model.addAttribute("userId", targetId);
        return "/users/update";
    }

    @PostMapping("/{userId}/update")
    public String updateUser(@PathVariable(name = "userId") String userId, User referenceUser) {
        User presentUser = userRepository.getOne(userId);

        if (!isValidPassword(presentUser.getPassword(), referenceUser.getPassword())) {
            return "redirect:/users";
        }

        updateUserProperties(presentUser, referenceUser);

        return "redirect:/users";
    }

    private boolean isValidPassword(String real, String expected) {
        if (real.equals(expected)) return true;
        else return false;
    }

    private void updateUserProperties(User presentUser, User referenceUser) {
        presentUser.setPassword(referenceUser.getPassword());
        presentUser.setName(referenceUser.getName());
        presentUser.setEmail(referenceUser.getEmail());
    }
}
