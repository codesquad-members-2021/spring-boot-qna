package com.codessquad.qna.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionController {

    @GetMapping("/questions/form")
    public String getForm(){
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(String writer, String title, String contents){
        System.out.printf("%s %s %s ", writer, title, contents);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showQuestionList(){
        return "qna/show";
    }

}
