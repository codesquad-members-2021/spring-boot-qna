package com.codesquad.qna.controller;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.User;
import com.codesquad.qna.exception.IllegalUserAccessException;
import com.codesquad.qna.service.QuestionService;
import com.codesquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        return "/qna/form";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        Question question = findQuestionWithSession(id, session);
        model.addAttribute(question);

        return "/qna/updateForm";
    }

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("questions", questionService.findAll());
        return "/qna/list";
    }

    @PostMapping()
    public String question(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        questionService.save(question, session);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Question question = questionService.findQuestionById(id);
        model.addAttribute("question", question);
        return "/qna/show";
    }

    @PutMapping("/{id}/form")
    public String update(@PathVariable("id") Long id, Question updatedQuestion, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        Question question = findQuestionWithSession(id, session);
        questionService.update(question, updatedQuestion);

        return "redirect:/questions";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        Question question = findQuestionWithSession(id, session);
        questionService.delete(question);

        return "redirect:/questions";
    }

    private Question findQuestionWithSession(Long id, HttpSession session) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionService.findQuestionById(id);

        if (!sessionedUser.isMatchedUserId(question.getWriter())) {
            throw new IllegalUserAccessException();
        }
        return question;
    }
}
