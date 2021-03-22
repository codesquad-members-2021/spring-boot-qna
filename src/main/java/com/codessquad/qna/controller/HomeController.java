package com.codessquad.qna.controller;


import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.service.QuestionService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final QuestionRepository questionRepository;
    private final QuestionService questionService;

    public HomeController(QuestionRepository questionRepository, QuestionService questionService) {
        this.questionRepository = questionRepository;
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String welcome(@PageableDefault(size = 15) Pageable pageable, Model model) {
        model.addAttribute("Questions", questionService.findQuestionList(pageable));
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("pre", pageable.next().getPageNumber() - 2);
        return "index";
    }

}
