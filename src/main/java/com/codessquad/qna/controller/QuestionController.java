package com.codessquad.qna.controller;

import com.codessquad.qna.model.Question;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    List<Question> questions = new ArrayList<>();

    @GetMapping("/questions/form")
    public String getForm(){
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(Question question){
        questions.add(question);
        return "redirect:/";
    }




}
