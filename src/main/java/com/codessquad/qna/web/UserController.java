package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.PasswordNotMatchException;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.web.HttpSessionUtils.*;

@Controller
@RequestMapping("/users")
public class UserController {

    //Todo : repository는 service 완성하고 삭제하기
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping
    public String create(User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        userService.showUserList(model);
        return "user/list";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("해당 유저가 존재하지 않습니다."));
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
        checkValidById(loggedinUser, id);
        model.addAttribute("user", loggedinUser);
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String update(@PathVariable Long id, Model model, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        checkValidById(loggedinUser, id);
        model.addAttribute("user", loggedinUser);
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateForm(@PathVariable Long id, String inputPassword, User updatedUser, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        checkValidById(loggedinUser, id);
        User user = loggedinUser;
        if (!user.isPasswordMatching(inputPassword)) {
            throw new PasswordNotMatchException();
        }
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/users";
    }

    private boolean checkValidById(User user, Long id) {
        if (!user.isIdMatching(id)) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }
        return true;
    }
}
