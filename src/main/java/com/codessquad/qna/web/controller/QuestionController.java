package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.question.QuestionRepository;
import com.codessquad.qna.web.domain.question.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping("/questions")
    public String create(Question question){
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public ModelAndView getQuestionDetail(@PathVariable long id){
        ModelAndView mav = new ModelAndView("/qna/show");
        mav.addObject("question", questionRepository.findById(id).get());
        return mav;
    }


    @GetMapping("/")
    public ModelAndView getHome(){
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("questions", questionRepository.findAll());
        return mav;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(){
        return "/errors/invalidInput";
    }

}
