package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.user.User;

import com.codessquad.qna.web.domain.user.UserRepository;
import com.codessquad.qna.web.exception.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable long id) {
        ModelAndView mav = new ModelAndView("user/profile");
        mav.addObject("user", userRepository.findById(id).get());
        return mav;
    }

    @GetMapping("/{id}/form")
    public ModelAndView updateForm(@PathVariable long id) {
        ModelAndView mav = new ModelAndView("user/updateForm");
        mav.addObject("user", userRepository.findById(id).get());
        return mav;
    }

    @PostMapping("/{userId}/update")
    public String updateProfile(User updatedUser, String oldPassword){
        for(int i = 0; i < users.size(); i++){
            User user = users.get(i);
            if (user.isMatchingPassword(oldPassword)) {
                users.set(i, updatedUser);
                break;
            }
        }
        return "redirect:/users";
    }

    private User findUserById(String userId){
        for(User user : users){
            if(user.isMatchingUserId(userId)){
                return user;
            }
        }
        throw new UserNotFoundException(userId);
    }

}
