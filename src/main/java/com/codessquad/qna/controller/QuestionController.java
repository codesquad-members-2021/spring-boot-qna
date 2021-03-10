package com.codessquad.qna.controller;

import com.codessquad.qna.model.Answer;
import com.codessquad.qna.model.Question;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

import static com.codessquad.qna.controller.HttpSessionUtils.isLoginUser;

@Controller
public class QuestionController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private final QuestionService questionService;
    private final AnswerService answerService;

    public QuestionController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
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
        List<Answer> answers = this.answerService.findAll(id);
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        logger.info("상세 질문 페이지 요청");
        return question.nonNull() ? "qna/show" : "redirect:/";
    }

    @GetMapping("/question/{id}/form")
    public String viewUpdateQuestion(@PathVariable("id") Long id, Model model, HttpSession session) {
        Question question = this.questionService.verifyQuestion(id, session);
        model.addAttribute("question", question);
        logger.info("질문 수정 페이지 요청");
        return question.nonNull() ? "qna/updateForm" : "redirect:/question/" + id;
    }

    @PutMapping("/question/{id}/form")
    public String updateQuestion(@PathVariable("id") Long id, Question question, HttpSession session) {
        boolean result = this.questionService.update(id, question, session);
        logger.info("질문 수정 요청");
        return result ? "redirect:/question/" + id : "redirect:/";
    }

    @DeleteMapping("/question/{id}")
    public String deleteQuestion(@PathVariable("id") Long id, HttpSession session) {
        boolean result = this.questionService.delete(id, session);
        logger.info("질문 삭제 요청");
        return result ? "redirect:/" : "redirect:/question/" + id;
    }

}
