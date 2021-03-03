package com.codessquad.qna.controller;

import com.codessquad.qna.entity.User;
import com.codessquad.qna.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

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
        return "user/list";
    }

    /**
     * @param id
     * @param model
     * @return only for users with the same id
     */
    @GetMapping("/users/{id}")
    public String getUserProfile(@PathVariable String id, Model model) {
        Optional<User> user = userService.getUser(id);
        if(user.isPresent()) {
            model.addAttribute("name", user.get().getName());
            model.addAttribute("email", user.get().getEmail());
        }
        return "user/profile";
    }

    /**
     * 유저 프로필 수정이 가능한 창으로 이동합니다.
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/users/{id}/form")
    public String updateUserProfileForm(@PathVariable String id, Model model) {
        Optional<User> user = userService.getUser(id);
        if(user.isPresent()){
            model.addAttribute("user", user);
        }
        return "user/updateForm";
    }

    /**
     * 유저 프로필을 해당 매개변수로 온 값으로 업데이트 합니다.
     * @param id
     * @param userId
     * @param password
     * @param name
     * @param email
     * @return
     */
    @PostMapping("/user/{id}/update")
    public String updateUserProfile(@PathVariable String id, String userId, String password, String name, String email) {
        userService.removeUser(id);
        User ChangeUser = new User(userId, password, name, email);
        userService.save(ChangeUser);
        return "redirect:/users";
    }

}
