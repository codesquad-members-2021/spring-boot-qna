package com.codessquad.qna.user;

import com.codessquad.qna.exception.UserNotFoundException;
import com.codessquad.qna.utils.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public ModelAndView getUserUpdateForm(@PathVariable Long id, HttpSession session) {
        User sessionUser = SessionUtils.getSessionUser(session);

        if (sessionUser.getId() != id) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        User user = userRepository.findById(id).get();
        return new ModelAndView("/user/updateForm", "user", user.toDTO());
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, UserDTO newUser, HttpSession session) {
        User existedUser = userRepository.findById(id).get();

        existedUser.checkPassword(newUser.getPassword());
        existedUser.update(newUser);

        userRepository.save(existedUser);
        SessionUtils.setSessionUser(session, existedUser);

        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));

        user.checkPassword(password);
        SessionUtils.setSessionUser(session, user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SessionUtils.removeSessionUser(session);

        return "redirect:/";
    }
}
