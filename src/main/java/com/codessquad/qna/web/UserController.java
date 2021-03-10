package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public String create(User user) {
        if(user == null){
            return "redirect:/create";
        }
        userRepository.save(user);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/users/{id}")
    public ModelAndView getOneUserProfile(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("user/profile");
        modelAndView.addObject("user", userRepository.findById(id).get());
        return modelAndView;
    }

    @GetMapping("/create/{id}")
    public ModelAndView editUserInfo(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("user/updateForm");
        modelAndView.addObject("user", userRepository.findById(id).get());
        return modelAndView;
    }

    @PostMapping("/{id}")
    public String update(@PathVariable long id, User updateUser) {
        User user = userRepository.findById(id).get();
        user.update(updateUser);

        userRepository.save(user);
        return "redirect:/list";
    }

}
