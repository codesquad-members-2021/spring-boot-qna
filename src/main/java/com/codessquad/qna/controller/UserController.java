    package com.codessquad.qna.controller;

    import com.codessquad.qna.domain.User;
    import com.codessquad.qna.repository.UserRepository;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;

    @Controller
    public class UserController {
        UserRepository userRepository = new UserRepository();

        @GetMapping("/users/form")
        public String createForm() {
            return "users/form";
        }

        @PostMapping("/users/create")
        public String createUser(User user){
            Logger logger = LoggerFactory.getLogger(UserController.class);
            User userForRegister = new User();

            userForRegister.setUserId(user.getUserId());
            userForRegister.setPassword(user.getPassword());
            userForRegister.setName(user.getName());
            userForRegister.setEmail(user.getEmail());

            userRepository.save(userForRegister);

            logger.info("User in UserRepository: " + user.toString());
            return "redirect:/users";
        }

        @GetMapping("/users")
        public String createUserList() {
            return "users/list";
        }

    }
