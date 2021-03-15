package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QnaController {

    private List<Question> questions = new ArrayList<>();


    @GetMapping("/questions/form")
    public String showQuestionForm() {
        return "/qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(Question question) {
        questions.add(question);
        question.setQuestionId(questions.size());
        System.out.println("question.getQuestionId() = " + question.getQuestionId());
        return "redirect:/";
    }

    @GetMapping("")
    public String loadHome(Model model) {
        model.addAttribute("questions", questions);
        System.out.println("questions = " + questions);
        return "index";
    }

    @GetMapping("questions/{questionId}")
    public String showQuestion(@PathVariable int questionId, Model model) {
        Question question = questions.get(questionId -1);
        model.addAttribute("question",question);
        return "/qna/show";
    }

}
