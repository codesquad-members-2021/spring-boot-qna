package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QnaController {
    QnaService qnaService;

    @Autowired
    public QnaController(QnaService qnaService) {
        this.qnaService = qnaService;
    }


    @PostMapping("/questions")
    public String createQuestion(Question question) {
        qnaService.save(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{index}")
    public String showQuestion(@PathVariable int index, Model model) {
        Question question = qnaService.findQuestionById(index);
        System.out.println(question);
        model.addAttribute("question", question);
        return "qna/show";
    }
}
