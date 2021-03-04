    package com.codessquad.qna.controller;    import com.codessquad.qna.domain.User;    import com.codessquad.qna.repository.UserRepository;    import org.slf4j.Logger;    import org.slf4j.LoggerFactory;    import org.springframework.stereotype.Controller;    import org.springframework.ui.Model;    import org.springframework.web.bind.annotation.GetMapping;    import org.springframework.web.bind.annotation.PathVariable;    import org.springframework.web.bind.annotation.PostMapping;    import java.util.List;    @Controller    public class UserController {        UserRepository userRepository = new UserRepository();        @PostMapping("/users")        public String createUser(User user){            Logger logger = LoggerFactory.getLogger(UserController.class);            User newUser = new User();            newUser.setUserId(user.getUserId());            newUser.setPassword(user.getPassword());            newUser.setName(user.getName());            newUser.setEmail(user.getEmail());            if (isRedundant(newUser)){                throw new IllegalStateException("이미 존재하는 사용자 아이디");            }            userRepository.save(newUser);            logger.info("User in UserRepository: " + user.toString());            return "redirect:/users";        }        private boolean isRedundant(User user){            return userRepository.isRedundant(user.getUserId());        }        @GetMapping("/users")        public String createUserList(Model model) {            Logger logger = LoggerFactory.getLogger(UserController.class);            List<User> users = userRepository.getAll();            logger.info("All Users in UserRepository: " + users.toString());            model.addAttribute("users", users);            return "users/list";        }        @GetMapping("/users/{userId}")        public String createProfile(@PathVariable(name = "userId") String targetId, Model model) {            Logger logger = LoggerFactory.getLogger(UserController.class);            User targetUser = userRepository.getOne(targetId);            model.addAttribute("user", targetUser);            logger.info("The User in detail: " + targetUser.toString());            return "users/profile";        }    }