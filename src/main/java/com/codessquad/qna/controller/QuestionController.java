package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.service.QuestionService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/questions")
public class QuestionController {

    private QuestionService questionService;
    private final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("")
    public String createQuestion(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        questionService.write(question, HttpSessionUtils.getUserFromSession(session));
        return "redirect:/";
    }

    @GetMapping("/{questionId}")
    public String renderQuestion(@PathVariable Long questionId, Model model) {
        Question findQuestion = questionService.findById(questionId);

        model.addAttribute("question", findQuestion);
        return "qna/show";
    }

    @GetMapping("/form")
    public String renderQuestionForm(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        return "qna/form";
    }

    @GetMapping("/{id}/form")
    public String renderUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/";
        }
        if (questionService.checkChangeable(id, HttpSessionUtils.getUserFromSession(session))) {
            Question findQuestion = questionService.findById(id);
            model.addAttribute("question", findQuestion);
            return "qna/updateForm";
        }
        return "";//todo : 에러 페이지로
    }

    @PutMapping("/{id}")
    public String questionUpdate(@PathVariable Long id, Question question, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/";
        }
        if (questionService.checkChangeable(id, HttpSessionUtils.getUserFromSession(session))) {
            questionService.update(id, question);
            return "redirect:/questions/" + id;
        }
        return "";//todo : 에러 페이지로
    }

    @DeleteMapping("/{id}")
    public String questionDelete(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/";
        }

        if (questionService.checkChangeable(id, HttpSessionUtils.getUserFromSession(session))) {
            questionService.delete(id);
            return "redirect:/";
        }
        return "";//todo : 에러 페이지로
    }

}
