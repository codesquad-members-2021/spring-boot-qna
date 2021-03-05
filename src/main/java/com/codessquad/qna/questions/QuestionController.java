package com.codessquad.qna.questions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping
    String createQuestion(Question question) {
        question.setDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        questionService.addQuestion(question);

        return "redirect:/";
    }

    @GetMapping("/{index}")
    String showQuestionDetail(@PathVariable int index, Model model) {
        Optional<Question> question = questionService.getQuestion(index);
        if (question.isPresent()) {
            model.addAttribute("question", question.get());
            return "qna/show";
        }

        return "redirect:/";
    }
}
