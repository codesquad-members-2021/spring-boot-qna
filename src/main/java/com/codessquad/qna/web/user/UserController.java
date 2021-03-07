package com.codessquad.qna.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository = new UserRepository();

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String user_create(User user) {
        userRepository.add(user);
        return "redirect:/users";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String user_list(Model model) {
        model.addAttribute("users", userRepository.getUsers());
        return "user/list";
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public String user_profile(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("user", userRepository.getUser(userId));
        return "user/profile";
    }

    @RequestMapping(value = "/{userId}/form", method = RequestMethod.GET)
    public String user_form(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("user", userRepository.getUser(userId));
        return "user/updateForm";
    }

    @RequestMapping(value = "/{userId}/update", method = RequestMethod.POST)
    public String user_update(@PathVariable("userId") String userId, User user) {
        userRepository.update(userId, user);
        return "redirect:/users";
    }
}
