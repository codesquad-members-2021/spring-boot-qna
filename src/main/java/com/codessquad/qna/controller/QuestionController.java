package com.codessquad.qna.controller;

import com.codessquad.qna.model.Question;
import com.codessquad.qna.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


import static com.codessquad.qna.controller.HttpSessionUtils.getUserFromSession;

@Controller
public class QuestionController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String viewMain(Model model) {
        model.addAttribute("questions", this.questionService.findAll());
        logger.info("메인 페이지 요청");
        return "index";
    }

    @GetMapping("/question/form")
    public String viewCreateQuestion(HttpSession session) {
        getUserFromSession(session);
        logger.info("질문 작성 페이지 요청");
        return "qna/form";
    }

    @PostMapping("/question/form")
    public String createQuestion(Question question, HttpSession session) {
        this.questionService.save(question, getUserFromSession(session));
        logger.info("질문 등록 요청");
        return "redirect:/";
    }

    @GetMapping("/question/{id}")
    public String viewQuestion(@PathVariable("id") Long id, Model model) {
        model.addAttribute("question", this.questionService.findById(id));
        logger.info("상세 질문 페이지 요청");
        return "qna/show";
    }

    @GetMapping("/question/{id}/form")
    public String viewUpdateQuestion(@PathVariable("id") Long id, Model model, HttpSession session) {
        model.addAttribute("question", this.questionService.verifyQuestion(id, getUserFromSession(session)));
        logger.info("질문 수정 페이지 요청");
        return "qna/updateForm";
    }

    @PutMapping("/question/{id}/form")
    public String updateQuestion(@PathVariable("id") Long id, Question question, HttpSession session) {
        this.questionService.update(id, question, getUserFromSession(session));
        logger.info("질문 수정 요청");
        return "redirect:/question/" + id;
    }

    @DeleteMapping("/question/{id}")
    public String deleteQuestion(@PathVariable("id") Long id, HttpSession session) {
        boolean result = this.questionService.delete(id, getUserFromSession(session));
        logger.info("질문 삭제 요청");
        return result ? "redirect:/" : "redirect:/question/" + id;
    }

}
