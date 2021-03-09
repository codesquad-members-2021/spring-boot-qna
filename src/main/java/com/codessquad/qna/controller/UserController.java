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

    @GetMapping("/users/{primaryKey}")
    private String displayProfile(@PathVariable Long primaryKey, Model model) {
        Objects.requireNonNull(primaryKey, "Exception: primaryKey가 NULL 값입니다.");

        model.addAttribute("users",userRepository.findById(primaryKey).get());

        // TODO. model.addAttribute("invalidMember", true);

        return "user/profile";
    }

    @GetMapping("users/{primaryKey}/form")
    private String changeMemberInfo(@PathVariable("primaryKey") Long targetKey, Model model) {
        Objects.requireNonNull(targetKey, "Exception: targetKey가 NULL 값입니다.");

        model.addAttribute("users",userRepository.findById(targetKey).get());
        //TODO.  model.addAttribute("invalidMember", true);

        return "user/updateForm";
    }


    @PostMapping("/users/{primaryKey}/update")
    private String updateMemberList(@PathVariable("primaryKey") Long targetKey, User updateUser, Model model) {
        Objects.requireNonNull(updateUser, "Exception: updateUser이 NULL 값입니다.");

        //TODO.  model.addAttribute("invalidPassword", true);
        if(userRepository.existsById(targetKey)){ // 넘어온 primaryKey 가 데이터베이스에 존재한다면
            User originUser = userRepository.findById(targetKey).get();
            userRepository.save(User.updateTargetProfile(originUser,updateUser));
            model.addAttribute("users",userRepository.findAll());
        }
        return "redirect:/users";
    }

}
