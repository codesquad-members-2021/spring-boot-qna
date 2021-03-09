package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.question.QuestionRepository;
import com.codessquad.qna.web.domain.question.Question;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String getQuestionDetail(@PathVariable long id, Model model){
        model.addAttribute("question", questionRepository.findById(id).get());
        return "/qna/show";
    }


    @GetMapping("/")
    public String getHome(Model model){
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(){
        return "/errors/invalidInput";
    }

}
