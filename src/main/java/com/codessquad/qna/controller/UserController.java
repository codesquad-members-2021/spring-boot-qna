    package com.codessquad.qna.controller;

    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;

    @Controller
    public class UserController {
        @GetMapping("/user/form")
        public String createForm() {
            return "users/form";
        }
    }
