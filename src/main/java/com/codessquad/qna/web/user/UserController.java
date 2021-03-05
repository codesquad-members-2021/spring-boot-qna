package com.codessquad.qna.web.user;

import com.codessquad.qna.web.user.User;
import com.codessquad.qna.web.user.UserMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserMapper userMapper = new UserMapper();

    @GetMapping("/signup")
    public String user_form() {
        return "user/form";
    }

    @PostMapping("/user/create")
    public String user_create(User user) {
        userMapper.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String user_list(Model model) {
        model.addAttribute("users", userMapper.getUsers());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String user_profile(@PathVariable("userId") String userId, Model model) { ;
        model.addAttribute("user", userMapper.getUser(userId));
        return "user/profile";
    }
}
