package com.codessquad.qna.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ModelAndView getUsers() {
        List<UserDTO> users = StreamSupport.stream(userRepository.findAll().spliterator(), true)
                .map(User::toDTO)
                .collect(Collectors.toList());

        return new ModelAndView("/user/list", "users", users);
    }

    @GetMapping("/{id}")
    public ModelAndView getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).get();
        return new ModelAndView("/user/profile", "user", user.toDTO());
    }

    @PostMapping
    public String createUser(UserDTO user) {
        userRepository.save(user.toEntity());
        return "redirect:/users";
    }

    @GetMapping("/{id}/form")
    public ModelAndView getUserUpdateForm(@PathVariable Long id) {
        User user = userRepository.findById(id).get();
        return new ModelAndView("/user/updateForm", "user", user.toDTO());
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, UserDTO newUser) {
        User existedUser = userRepository.findById(id).get();

        existedUser.checkPassword(newUser.getPassword());
        existedUser.update(newUser);

        userRepository.save(existedUser);

        return "redirect:/users";
    }
}
