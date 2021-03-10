package com.codessquad.qna.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping
    public ModelAndView getQuestions() {
        return new ModelAndView("/qna/list", "questions", questionRepository.findAll());
    }

    @PostMapping
    public String createQuestions(Question question, HttpSession session) {
        if (session.getAttribute("sessionedUser") == null) {
            return "redirect:/users/login/form";
        }

        questionRepository.save(question);
        return "redirect:/questions";
    }

    @GetMapping("/{id}")
    public ModelAndView getQuestion(@PathVariable Long id) {
        Question question = questionRepository.findById(id).get();
        return new ModelAndView("/qna/show", "question", question);
    }
}
