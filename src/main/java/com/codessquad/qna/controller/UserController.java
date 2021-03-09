package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {
    @Autowired // 생성자나 세터 등을 사용하여 의존성 주입을 하려고 할 때, 해당 빈을 찾아서 주입?? 나중에 공부하자
    private UserRepository userRepository;

    private List<User> userList = new ArrayList<>();

    @PostMapping("/user/create")
    private String register(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    private String getMemberList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    private String displayProfile(@PathVariable("userId") String userId, Model model) {
        userId = Objects.requireNonNull(userId, "Exception: userId가 NULL 값입니다.");

        for (User findUser : userList) {
            if (findUser.getUserId().equals(userId)) {
                model.addAttribute("invalidMember", false);
                model.addAttribute("userID", findUser.getUserId());
                model.addAttribute("email", findUser.getEmail());
            } else {
                model.addAttribute("invalidMember", true);
            }
        }
        return "user/profile";
    }

    @GetMapping("users/{userId}/form")
    private String changeMemberInfo(@PathVariable("userId") String userId, Model model) {
        userId = Objects.requireNonNull(userId, "Exception: userId가 NULL 값입니다.");

        for (User findUser : userList) {
            if (Objects.equals(findUser.getUserId(), userId)) {
                model.addAttribute("invalidMember", false);
                model.addAttribute("userID", findUser.getUserId());
                model.addAttribute("password", findUser.getPassword());
                model.addAttribute("name", findUser.getName());
                model.addAttribute("email", findUser.getEmail());
                break;
            } else {
                model.addAttribute("invalidMember", true);
            }
        }
        return "user/updateForm";
    }

    /*
    @PostMapping("/users/{userId}/update")
    private String updateMemberList(User updateUser, Model model) {

        for (int index = 0; index < userList.size(); index++) {
            boolean idCheck = User.checkId(userList.get(index),updateUser); // 아이디 유효성 체크
            boolean findPasswordCheck = User.checkPassword(userList.get(index),updateUser); // 비밀번호 유효성 체크

            if (idCheck && findPasswordCheck) {
                model.addAttribute("invalidPassword", false);
                userList.get(index).setName(Objects.toString(updateUser.getName(), "")); // null default
                userList.get(index).setEmail(Objects.toString(updateUser.getEmail(), "")); // null default
            } else {
                model.addAttribute("invalidPassword", true);
            }
            model.addAttribute("users", userList);
        }
        return "redirect:/users";
    }
*/
}
