package com.codessquad.qna.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/form")
    public String viewForm() {
        logger.info("회원가입 페이지 요청");
        return "user/form";
    }

}
