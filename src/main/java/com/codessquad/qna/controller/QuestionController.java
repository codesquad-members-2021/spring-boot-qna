package com.codessquad.qna.controller;

import com.codessquad.qna.exception.QuestionNotFoundException;
import com.codessquad.qna.repository.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @GetMapping
    public String questionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/form")
    public String qnaInputPage() {
        return "qna/questionInputForm";
    }

    @PostMapping("/form")
    public String newQuestion(Question question) {
        LocalDateTime dateTime = LocalDateTime.now();
        String dateTimeString = dateTime.format(dateTimeFormatter);
        question.setDateTime(dateTimeString);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String question(@PathVariable("id") Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new));
        return "qna/questionDetail";
    }
}
