package com.codessquad.qna.Controller;
import static com.codessquad.qna.utils.SessionUtil.*;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;


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
    public String list(Model model, HttpSession session) {
        model.addAttribute("userlist",userRepository.findAll());
        return "user/list";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id,User updatedUser,HttpSession session) throws Exception {
        User sessionUser = (User)session.getAttribute(SESSION_KEY_LOGIN_USER);
        //User currentUser = userRepository.findById(id).orElseThrow(() -> new Exception("데이터 검색에 실패하였습니다"));
        if(sessionUser == null) {
            return "redirect:/user/loginForm";
        }

        if(sessionUser.equals(updatedUser)) {
            sessionUser.update(updatedUser);
        }

        userRepository.save(sessionUser);
        logger.info("update User {}",sessionUser.getUserId());

        return "redirect:/user";
    }

    @GetMapping("/{id}/form")
    public String getUserupdateForm(@PathVariable Long id , Model model,HttpSession session) throws Exception{

        User loginUser = (User)session.getAttribute(SESSION_KEY_LOGIN_USER);
        model.addAttribute("user",loginUser);

        return "user/updateForm";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String loginProcess(String userId, String password, HttpSession session) {
        User foundUser = userRepository.findByUserId(userId);

        if(foundUser == null) {//중복 코드가 심해진다.
            logger.info("Login Failure");
            return "redirect:/user/form";
        }

        if(!foundUser.isMatchingPassword(password)) {
            logger.info("Login Failure");
            return "redirect:/user/form";
        }
        logger.info("Login Success");
        session.setAttribute(SESSION_KEY_LOGIN_USER,foundUser);

        return  "redirect:/";//로그인 끝나면 메인페이지로 이
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        logger.info("Logout Out ???? 왜 이러는기야!");
        session.removeAttribute("SESSION_KEY_LOGIN_USER");
        return "redirect:/";
    }
}
