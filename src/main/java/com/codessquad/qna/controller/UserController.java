package com.codessquad.qna.controller;

import com.codessquad.qna.entity.User;
import com.codessquad.qna.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * @param userId
     * @param password
     * @param name
     * @param email
     * @return redirect User list (/users)
     */
    @PostMapping("/user")
    public String createAccount(String userId, String password, String name, String email) {
        User user = new User(userId, password, name, email);
        userService.save(user);
        logger.info(user.toString());
        return "redirect:/users";
    }

    /**
     * @param model
     * @return All User Accounts to List
     */
    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "list";
    }

}
