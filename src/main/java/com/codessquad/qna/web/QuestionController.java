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

    @GetMapping("/")
    public String viewQuestionList(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/questions/{questionId}")
    public String viewQuestion(@PathVariable int questionId, Model model) {
        Question question = questions.get(questionId-1);
        model.addAttribute("question", question);
        return "/qna/show";
    }

    @PostMapping("/questions")
    public String createQuestion(Question question) {
        int id = questions.size() + 1;
        question.setId(id);
        questions.add(question);
        return "redirect:/";
    }

}
