package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;
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
    private String getMemberList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/users/{primaryKey}")
    private String displayProfile(@PathVariable("primaryKey") Long targetKey, Model model) {
        Objects.requireNonNull(targetKey, "Exception: targetKey가 NULL 값입니다.");

        Optional<User> userOptional = userRepository.findById(targetKey);
        User findUserData = userOptional.orElseThrow(NoSuchElementException::new);

        model.addAttribute("users", findUserData);

        return "user/profile";
    }

    @GetMapping("users/{primaryKey}/form") //m 개인정보 수정
    private String changeMemberInfo(@PathVariable("primaryKey") Long targetKey, Model model, HttpSession session) {
        Objects.requireNonNull(targetKey, "Exception: targetKey가 NULL 값입니다.");

        final User targetUserData = userRepository.findById(targetKey).orElseThrow(NoSuchElementException::new); // () -> new NoSuchElementException()
        final Object sessionValue = session.getAttribute("sessionedUser");

        if(sessionValue != null){
            User sessionUser = (User)sessionValue;
            if (targetUserData.eqaulId(sessionUser.getUserId())) {
                model.addAttribute("users", targetUserData);
                return "user/updateForm";
            }
        }
        model.addAttribute("errorMessage","타인의 정보는 열람할 수 없습니다!");
        return "error/404"; //m 세션아이디와 다를 경우 이동
    }
    @PutMapping("/users/{primaryKey}/update")
    private String updateMemberList(@PathVariable Long primaryKey, User updateUserData, Model model) {

        updateUserData.setPrimaryKey(primaryKey); //m 테이블이 생성될 때, PK가 생기므로. 임의 지정
        Optional<User> userOptional = userRepository.findById(primaryKey);
        User originUserData = userOptional.orElseThrow(NoSuchElementException::new); // () -> new NoSuchElementException()
        userRepository.save(originUserData.update(updateUserData));

        model.addAttribute("users", userRepository.findAll());

        return "redirect:/users";
    }

    @PostMapping("/user/login")
    public String login(String userId, String password, HttpSession session){
        userId = Objects.toString(userId,""); //m userId의 값이 null일 경우 ""으로 초기화
        password = Objects.toString(password,""); //m userId의 값이 null일 경우 ""으로 초기화
        User findUser = userRepository.findByUserId(userId);

        if(findUser == null){
            return "user/login_failed";
        }
        if(!findUser.eqaulId(userId) || !findUser.equalPassword(password)){
            return "user/login_failed";
        }
        session.setAttribute("sessionedUser",findUser);
        return "index";
    }

}
