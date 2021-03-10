package com.codessquad.qna.controller;

import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    private final QuestionRepository questionRepository;

    @Autowired
    public IndexController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/")
    private ModelAndView questionsList() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("questions", questionRepository.findAll());
        return mav;
    }
}
