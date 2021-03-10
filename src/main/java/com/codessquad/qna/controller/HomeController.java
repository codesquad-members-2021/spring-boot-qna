package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.service.QnaService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final QnaService qnaService;

    public HomeController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Question> questions = qnaService.findAll();
        model.addAttribute("questions", questions);
        return "/home";
    }
}
