package com.codessquad.qna.web;

import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
@RequestMapping("/users")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/form")
    public String getUserFormPage() {
        return "user/form";
    }

    @PostMapping("/")
    public String createUser(User user) {
        userRepository.save(user);
        logger.debug("user : {}", user);
        return "redirect:/users/";
    }

    @GetMapping("/")
    public String getUserList(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable Long id, HttpSession session) {
        Object userAsObject = session.getAttribute("user");

        if (userAsObject == null) {
            logger.error("user : 해당 회원이 존재하지 않습니다.");
            return "redirect:/users/form";
        }

        User user = (User) userAsObject;

        if (!user.matchId(id)) {
            logger.error("user : 다른 사용자의 정보를 열람할 수 없습니다.");
            return "redirect:/";
        }

        logger.debug("user : {}", user);

        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable Long id, Model model,HttpSession session) {
        User loginedUser = (User) session.getAttribute("user");

        if(loginedUser == null) {
            throw new IllegalStateException("로그인되지 않았습니다.");
        }

        if (!loginedUser.matchId(id)) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }

//        Optional<User> user = userRepository.findById(id);

//        model.addAttribute("user", user.get());
        logger.debug("user : {}", loginedUser);

        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String prevPassword,
                         User updateUser, HttpSession session) {
        User loginedUser = (User) session.getAttribute("user");

        if(loginedUser == null) {
            throw new IllegalStateException("로그인되지 않았습니다.");
        }

        if(!loginedUser.matchId(id)) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }

        User user = userRepository.findById(id).get();

        if (!user.matchPassword(prevPassword)) {
            return "redirect:/users/";
        }

        user.update(updateUser);
        userRepository.save(user);

        logger.debug("user : {}", updateUser);

        return "redirect:/users/";
    }

    @GetMapping("/loginForm")
    public String getLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            logger.error("등록된 회원이 존재하지 않습니다.");
            return "user/login_failed";
        }

        if (!user.matchPassword(password)) {
            logger.error("로그인에 실패하셨습니다.");
            return "user/login_failed";
        }

        logger.debug("login : {}", user);
        session.setAttribute("user", user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
}
