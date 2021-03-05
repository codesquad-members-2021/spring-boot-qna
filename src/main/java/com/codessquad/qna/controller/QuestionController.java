package com.codessquad.qna.controller;

import com.codessquad.qna.model.Question;
import com.codessquad.qna.repository.Questions;
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
    private Questions questions = new Questions();

    @GetMapping("/")
    public String viewMain(Model model) {
        model.addAttribute("questions", this.questions.getAllQuestion());
        return "index";
    }

    @PostMapping("/question/form")
    public String createQuestion(Question question) {
        this.questions.addQuestion(question);
        logger.info("질문 등록 요청");
        return "redirect:/";
    }

    @GetMapping("/question/{id}")
    public String viewQuestion(@PathVariable("id") int id, Model model) {
        model.addAttribute("question", this.questions.findQuestion(id));
        logger.info("상제 질문 페이지 요청");
        return "qna/show";
    }

}
