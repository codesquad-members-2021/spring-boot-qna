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
        question.setId(nextId());
        questions.add(question);
        System.out.println(question.getId());
        return "redirect:/";
    }

    public int nextId(){
        return questions.size() + 1;
    }




}
