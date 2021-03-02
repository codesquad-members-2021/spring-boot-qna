    package com.codessquad.qna.controller;

    import com.codessquad.qna.domain.User;
    import com.codessquad.qna.repository.UserRepository;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;

    @Controller
    public class UserController {
        UserRepository userRepository = new UserRepository();

        @GetMapping("/user/form")
        public String createForm() {
            return "users/form";
        }

        @PostMapping("/user/create")
        public String createUser(User user){
            User userForRegister = new User();
            userForRegister.setUserID(user.getUserID());
            userForRegister.setPassword(user.getPassword());
            userForRegister.setName(user.getName());
            userForRegister.setEmail(user.getEmail());

            userRepository.save(userForRegister);
            return "redirect:/users";
        }
    }
