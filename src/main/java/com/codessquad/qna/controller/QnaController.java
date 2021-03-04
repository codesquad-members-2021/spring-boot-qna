package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class QnaController {
    QnaService qnaService;

    @Autowired
    public QnaController(QnaService qnaService) {
        this.qnaService = qnaService;
    }


    @PostMapping("")
    public String createQuestion(Question question) {
        qnaService.save(question);
        return "redirect:/";
    }

    @GetMapping("/{index}")
    public String showQuestion(@PathVariable int index, Model model) {
        Question question = qnaService.findQuestionById(index);
        model.addAttribute("question", question);
        return "/qna/show";
    }
}
