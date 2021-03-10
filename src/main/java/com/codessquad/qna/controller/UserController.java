package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Controller
public class UserController {
    private final UserRepository userRepository;

    @Autowired // 생성자나 세터 등을 사용하여 의존성 주입을 하려고 할 때, 해당 빈을 찾아서 주입?? 나중에 공부하자
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/user/create")
    private String register(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    private ModelAndView getMemberList() {
        ModelAndView mav = new ModelAndView("user/list");
        mav.addObject("users", userRepository.findAll());
        return mav;
    }

    @GetMapping("/users/{primaryKey}")
    private ModelAndView displayProfile(@PathVariable("primaryKey") Long targetKey) {
        Objects.requireNonNull(targetKey, "Exception: targetKey가 NULL 값입니다.");

        Optional<User> userOptional = userRepository.findById(targetKey);
        User findUserData = userOptional.orElseThrow(NoSuchElementException::new);

        ModelAndView mav = new ModelAndView("user/profile");
        mav.addObject("users", findUserData);

        // TODO. model.addAttribute("invalidMember", true);

        return mav;
    }

    @GetMapping("users/{primaryKey}/form")
    private ModelAndView changeMemberInfo(@PathVariable("primaryKey") Long targetKey) {
        Objects.requireNonNull(targetKey, "Exception: targetKey가 NULL 값입니다.");

        Optional<User> userOptional = userRepository.findById(targetKey);
        User originUserData = userOptional.orElseThrow(NoSuchElementException::new); // () -> new NoSuchElementException()

        ModelAndView mav = new ModelAndView("user/updateForm");
        mav.addObject("users", originUserData);

        return mav;
    }


    @PutMapping("/users/{primaryKey}/update")
    private ModelAndView updateMemberList(@PathVariable Long primaryKey, User updateUserData) {

        updateUserData.setPrimaryKey(primaryKey); //m 테이블이 생성될 때, PK가 생기므로. 임의 지정
        Optional<User> userOptional = userRepository.findById(primaryKey);
        User originUserData = userOptional.orElseThrow(NoSuchElementException::new); // () -> new NoSuchElementException()
        userRepository.save(originUserData.update(updateUserData));

        ModelAndView mav = new ModelAndView("redirect:/users");
        mav.addObject("users", userRepository.findAll());

        return mav;
    }

}
