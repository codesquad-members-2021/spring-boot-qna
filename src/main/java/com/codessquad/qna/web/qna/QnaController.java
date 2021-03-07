package com.codessquad.qna.web.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QnaController {
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions")
    public String createQuestion(Question newQuestion) {
        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getQuestionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{questionId}")
    public String getOneQuestion(@PathVariable("questionId") long questionId, Model model) {
        Question foundQuestion = questionRepository.findById(questionId).orElse(null);
        model.addAttribute("question", foundQuestion);
        return "qna/show";
    }

}
