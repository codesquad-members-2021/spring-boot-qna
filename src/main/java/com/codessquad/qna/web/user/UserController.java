package com.codessquad.qna.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private List<User> userList = new ArrayList<>();

    @PostMapping("user/create")
    public String onRegister(String userId, String password, String name, String email) {
        logger.info("onRegister called");
        User createdUser = new User(userId, password, name, email);
        logger.info("createdUser : " + createdUser);
        userList.add(createdUser);
        logger.info("userList.size()" + userList.size());

        return "index.html";
    }
}
