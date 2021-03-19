package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NoUserException;
import com.codessquad.qna.exception.NotMatchException;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.web.HttpSessionUtils.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId).orElseThrow(NoUserException::new);
        if (!user.isPasswordMatching(password)) {
            throw new IllegalStateException("아이디 혹은 비밀번호가 일치하지 않습니다.");
        }
        session.setAttribute(USER_SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        if (!loggedinUser.isIdMatching(id)) {
            throw new IllegalStateException("자신의 정보만 확인할 수 있습니다.");
        }
        model.addAttribute("user", loggedinUser);
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String update(@PathVariable Long id, Model model, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        if (!loggedinUser.isIdMatching(id)) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }
        model.addAttribute("user", loggedinUser);
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateForm(@PathVariable Long id, String inputPassword, User updatedUser, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        if (!loggedinUser.isIdMatching(id)) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }
        User user = loggedinUser;
        if (!user.isPasswordMatching(inputPassword)) {
            throw new NotMatchException();
        }
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/users";
    }
}
