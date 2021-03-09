package com.codessquad.qna.controller;

import com.codessquad.qna.model.Question;
import com.codessquad.qna.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String viewMain(Model model) {
        model.addAttribute("questions", this.questionService.findAll());
        return "index";
    }

    @GetMapping("/question/form")
    public String viewQuestion() {
        logger.info("질문 작성 페이지 요청");
        return "qna/form";
    }

    @PostMapping("/question/form")
    public String createQuestion(Question question) {
        this.questionService.save(question);
        logger.info("질문 등록 요청");
        return "redirect:/";
    }

    @GetMapping("/question/{id}")
    public String viewQuestion(@PathVariable("id") Long id, Model model) {
        Question question = this.questionService.findById(id);
        model.addAttribute("question", question);
        logger.info("상제 질문 페이지 요청");
        return question.notNull() ? "qna/show" : "redirect:/";
    }

}
