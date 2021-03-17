package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
//    private final QuestionService questionService;

    @GetMapping("/")
    public String goHome() {

        return "forward:/questions"; // 옛날방식
    }
    //// 서블릿 두 번 :: 비효율적 ;;;
    /// 자바지기 영상
}
