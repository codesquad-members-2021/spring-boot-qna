package com.codessquad.qna.controller;

import com.codessquad.qna.model.Question;
import com.codessquad.qna.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.controller.HttpSessionUtils.isLoginUser;

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
    public String viewQuestion(HttpSession session) {
        logger.info("질문 작성 페이지 요청");
        return isLoginUser(session) ? "qna/form" : "redirect:/user/login";
    }

    @PostMapping("/question/form")
    public String createQuestion(Question question, HttpSession session) {
        boolean result = this.questionService.save(question, session);
        logger.info("질문 등록 요청");
        return result ? "redirect:/" : "redirect:/user/login";
    }

    @GetMapping("/question/{id}")
    public String viewQuestion(@PathVariable("id") Long id, Model model) {
        Question question = this.questionService.findById(id);
        model.addAttribute("question", question);
        logger.info("상제 질문 페이지 요청");
        return question.nonNull() ? "qna/show" : "redirect:/";
    }

    @GetMapping("/question/{id}/{writer}/form")
    public String viewUpdateQuestion(@PathVariable("id") Long id, @PathVariable("writer") String writer, Model model, HttpSession session) {
        Question question = this.questionService.verifyQuestion(id, writer, session);
        model.addAttribute("question", question);
        logger.info("질문 수정 페이지 요청");
        return question.nonNull() ? "qna/updateForm" : "redirect:/";
    }

    @PutMapping("/question/{id}/{writer}/form")
    public String updateQuestion(@PathVariable("id") Long id, @PathVariable("writer") String writer, Question question, HttpSession session) {
        boolean result = this.questionService.update(id, writer, question, session);
        logger.info("질문 수정 요청");
        return result ? "redirect:/question/" + id : "redirect:/";
    }

    @DeleteMapping("/question/{id}/{writer}")
    public String deleteQuestion(@PathVariable("id") Long id, @PathVariable("writer") String writer, HttpSession session) {
        boolean result = this.questionService.delete(id, writer, session);
        logger.info("질문 삭제 요청");
        return result ? "redirect:/" : "redirect:/question/" + id;
    }

}
