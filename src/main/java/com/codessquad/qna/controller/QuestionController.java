package com.codessquad.qna.controller;

import com.codessquad.qna.model.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class QuestionController {

    private List<Question> questions = new ArrayList<Question>();

    @PostMapping("/questions")
    public String create(Question question){
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model){
        model.addAttribute("questions",questions);
        return "index";
    }
}
