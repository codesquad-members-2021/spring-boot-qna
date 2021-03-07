package com.codessquad.qna.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private List<User> users = Collections.synchronizedList(new ArrayList<>(User.getDummyData()));

    @GetMapping
    public ModelAndView getUsers() {
        return new ModelAndView("/user/list", "users", users);
    }

    @GetMapping("/{id}")
    public ModelAndView getUser(@PathVariable int id) {
        logger.debug("id : " + id);
        return new ModelAndView("/user/profile", "user", users.get(id));
    }

    @PostMapping
    public String createUser(User user) {
        logger.debug(user.toString());
        users.add(user);

        return "redirect:/users";
    }

    @GetMapping("/{id}/form")
    public ModelAndView getUserUpdateForm(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("/user/form");
        modelAndView.addObject("user", users.get(id));
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable int id, User user) {
        users.set(id, user);
        return "redirect:/users";
    }
}
