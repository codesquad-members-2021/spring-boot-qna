package com.codessquad.qna.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<User> users = Collections.synchronizedList(new ArrayList<>(Arrays.asList(
            new User("javajigi", "1234", "자바지기", "javajigi@sample.net"),
            new User("slipp", "1234", "슬립", "slipp@sample.net")
    )));

    @GetMapping
    public ModelAndView getUsers() {
        return new ModelAndView("/user/list", "users", users);
    }

    @GetMapping("/form")
    public String getForm() {
        return "/user/form";
    }

    @PostMapping
    public String createUser(User user) {
        logger.debug(user.toString());
        users.add(user);

        return "redirect:/users";
    }
}
