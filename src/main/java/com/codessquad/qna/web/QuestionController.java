package com.codessquad.qna.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questions = new ArrayList<>();

    @PostMapping("/questions")
    public String createQuestion(Question question, Model model){
        questions.add(question);
        question.setIndex(questions.indexOf(question)+1);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getQuestions(Model model){
        model.addAttribute("questions", questions);
        return "/index";
    }

    @GetMapping("/questions/{index}")
    public String getQuestion(@PathVariable int index, Model model){
        Question question = questions.get(index -1);
        model.addAttribute("question", question);
        return "/qna/show";
    }
}
