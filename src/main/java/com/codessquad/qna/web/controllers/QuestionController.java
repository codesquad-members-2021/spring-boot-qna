package com.codessquad.qna.web.controllers;

import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions")
    public String postQuestionForm(Question question) {
        question.setDateTime(presentDateTime());

        questionRepository.save(question);
        return "redirect:/";
    }

    private String presentDateTime() {
        SimpleDateFormat format = new SimpleDateFormat(Question.DATE_PATTERN);
        Calendar time = Calendar.getInstance();
        return format.format(time.getTime());
    }

    @GetMapping("/questions")
    public String getIndex(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String getQuestionShow(@PathVariable("id") Long id, Model model) {
        Question question = questionRepository.findById(id).get();
        model.addAttribute("question", question);
        return "qna/show";
    }

}
