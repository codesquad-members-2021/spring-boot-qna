package com.codessquad.qna.Controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import com.codessquad.qna.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.SessionUtil.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String userListShow() {
        return "redirect:/user/list";
    }

    @GetMapping("/form")
    public String signUpForm() {
        logger.info("signUpForm >> users/form.html: in");
        return "user/form";
    }

    @PostMapping("/create")
    public String userCreate(User user) {
        userRepository.save(user);
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("userlist", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String showProfile(@PathVariable Long id, Model model) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("데이터 검색에 실패하였습니다"));
        model.addAttribute("user", user);
        return "user/profile";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, String pastPassword, User updatedUser, HttpSession session) throws Exception {

        User sessionUser = getLoginUser(session);
        User currentUser = userRepository.findById(id).orElseThrow(() -> new Exception("데이터 검색에 실패하였습니다"));

        if (!currentUser.isMatchingPassword(pastPassword)) {
            logger.info("password is not Matching, please re-try ");
            return "redirect:/user/login";
        }

        if (sessionUser == null) {
            return "redirect:/user/login";
        }

        if (sessionUser.equals(updatedUser)) {
            sessionUser.update(updatedUser);
        }

        userRepository.save(sessionUser);
        logger.info("update User {}", sessionUser.getUserId());

        return "redirect:/user";
    }

    @GetMapping("/{id}/form")
    public String getUserUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        User foundUser = userRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!isValidUser(session, foundUser)) {
            logger.info("Login Failure : wrong password");
            return "redirect:/user/form";
        }
        User loginUser = getLoginUser(session);
        model.addAttribute("user", loginUser);

        return "user/updateForm";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String loginProcess(String userId, String password, HttpSession session) {
        User foundUser = userRepository.findByUserId(userId).orElseThrow(NotFoundException::new); //get 안티패턴 수정해야함@@@@@@@@@@@@

        if (!foundUser.isMatchingPassword(password)) {
            logger.info("Login Failure : wrong password");
            return "redirect:/user/form";
        }

        logger.info("Login Success");
        setLoginUser(session, foundUser);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        logger.info("Logout Out Success");
        removeLoginUser(session);
        return "redirect:/";
    }

}
