package com.codessquad.qna.controller.user;

import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String user_create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String user_list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String user_profile(@PathVariable("id") Long id, Model model) {
        // TODO: 조회에 실패했을 경우에 대한 예외 처리 필요; 나중에 findById 로 orElseThrow 하자
        User user = userRepository.getOne(id);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @RequestMapping(value = "/{id}/form", method = RequestMethod.GET)
    public String user_form(@PathVariable("id") Long id, Model model) {
        // TODO: 조회에 실패했을 경우에 대한 예외 처리 필요; 나중에 findById 로 orElseThrow 하자
        User user = userRepository.getOne(id);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String user_update(@PathVariable("id") Long id, User updateUser) {
        // TODO: 조회에 실패했을 경우에 대한 예외 처리 필요; 나중에 findById 로 orElseThrow 하자
        User user = userRepository.getOne(id);
        user.update(updateUser);
        userRepository.save(user); // NOTE: findById 와 다르게, getOne 은 dirtyCheck 가 적용되지 않는다.
        return "redirect:/users";
    }
}
