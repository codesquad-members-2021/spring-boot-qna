package com.codessquad.qna.controller;

import com.codessquad.qna.utils.HttpSessionUtils;
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
    public final static boolean UPDATE_FALSE = false;
    public final static boolean UPDATE_TRUE = true;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/create")
    private String register(User user) {
        userService.save(user, UPDATE_FALSE);
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
        userService.authenticateOfId(userService.getById(targetKey), HttpSessionUtils.getLoginUserOf(session));
        model.addAttribute("users", HttpSessionUtils.getLoginUserOf(session));
        return "user/updateForm";
    }

    @PutMapping("/users/{primaryKey}/update")
    private String updateMemberList(@PathVariable Long primaryKey, User updateUserData, Model model) {
        updateUserData.setPrimaryKey(primaryKey); //m 테이블이 생성될 때, PK가 생기므로. 임의 지정
        User originUserData = userService.getById(primaryKey);

        userService.save(userService.update(originUserData, updateUserData), UPDATE_TRUE);
        model.addAttribute("users", userService.getList());
        return "redirect:/users";
    }

    @PostMapping("/user/login")
    public String login(String userId, String password, HttpSession session) {
        userService.checkValidOfLogin(userId, password); //m null 일 경우 내부에서 예외처리됨.
        HttpSessionUtils.setAttribute(session, userService.getById(userId));
        return "redirect:/";
    }

    @GetMapping("/logout") // 로그아웃은 주로 포스트맵핑을 사용한다고 하는데, 아직 잘 모르겠어서 변경하지 않음.
    private String logout(HttpSession session) {
        HttpSessionUtils.removeAttribute(session);
        return "redirect:/";
    }

}
