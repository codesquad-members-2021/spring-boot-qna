package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

    private QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping("/question")
    public String createQuestion(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{questionId}")
    public String renderQuestion(@PathVariable int questionId, Model model) {
        Question getQuestion = questionRepository.findById(questionId);

        model.addAttribute("question", getQuestion);
        return "/qna/show";
    }

}
