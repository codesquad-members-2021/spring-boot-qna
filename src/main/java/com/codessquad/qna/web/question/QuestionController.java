package com.codessquad.qna.web.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {
    QuestionRepository questionRepository = new QuestionRepository();

    @PostMapping("/question/create")
    public String question_create(Question question) {
        questionRepository.add(question);
        return "redirect:/questions";
    }

    @GetMapping("/questions")
    public String question_list(Model model) {
        model.addAttribute("questions", questionRepository.getQuestions());
        return "question/list";
    }

    @GetMapping("/questions/{questionId}")
    public String question_profile(@PathVariable("questionId") int questionId, Model model) {
        model.addAttribute("question", questionRepository.getQuestion(questionId));
        return "question/show";
    }
}
