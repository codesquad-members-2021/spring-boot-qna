package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/create")
    private String register(User user) {
        userService.register(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    private String getMemberList(Model model) {
        model.addAttribute("users", userService.getList());
        return "user/list";
    }

    @GetMapping("/users/{primaryKey}")
    private String displayProfile(@PathVariable("primaryKey") Long targetKey, Model model) {
        model.addAttribute("users", userService.getById(targetKey));

        return "user/profile";
    }

    @GetMapping("users/{primaryKey}/form") //m 개인정보 수정
    private String changeMemberInfo(@PathVariable("primaryKey") Long targetKey, Model model, HttpSession session) {
        final Object sessionValue = session.getAttribute("sessionedUser");
        if(sessionValue != null){
            User sessionUser = (User)sessionValue;
            if (userService.matchingId(userService.getById(targetKey),sessionUser)) {
                model.addAttribute("users", sessionUser);
                return "user/updateForm";
            }
        }
        model.addAttribute("errorMessage","타인의 정보는 열람할 수 없습니다!");
        return "error/404"; //m 세션아이디와 다를 경우 이동
    }

    @PutMapping("/users/{primaryKey}/update")
    private String updateMemberList(@PathVariable Long primaryKey, User updateUserData, Model model) {

        updateUserData.setPrimaryKey(primaryKey); //m 테이블이 생성될 때, PK가 생기므로. 임의 지정
        userService.register(userService.update(userService.getById(primaryKey),updateUserData));
        model.addAttribute("users", userService.getList());

        return "redirect:/users";
    }

    @PostMapping("/user/login")
    public String login(String userId, String password, HttpSession session){
        if(!userService.checkValidOfLogin(userId,password)){
            return "user/login_failed";
        }
        session.setAttribute("sessionedUser",userService.getById(userId));
        return "redirect:/";
    }
    @GetMapping("/logout")
    private String logout(HttpSession session){
        session.removeAttribute("sessionedUser");
        return "redirect:/";
    }

}
