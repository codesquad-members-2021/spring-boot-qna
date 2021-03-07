package com.codessquad.qna.controller;

import com.codessquad.qna.model.Question;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

    @GetMapping("/questions/form")
    public String getForm(){
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(Question question){
        System.out.println(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showQuestionList(){
        return "qna/show";
    }

}
