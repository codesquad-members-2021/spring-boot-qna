package com.codessquad.qna.Controller;

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

    private final static String SESSION_KEY_USER_OBJECT = "loginUser";

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
    public String updateUser(@PathVariable Long id,User newUser) throws Exception {
        User currentUser = userRepository.findById(id).orElseThrow(() -> new Exception("데이터 검색에 실패하였습니다"));
        currentUser.update(newUser);
        userRepository.save(currentUser);
        logger.info("update User : " + currentUser.toString());
        return "redirect:/user";
    }

    @GetMapping("/{id}/form")
    public String getUserupdateForm(@PathVariable Long id , Model model) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user",user);
        return "user/updateForm";
    }

}
