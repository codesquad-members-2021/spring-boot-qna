package com.codesquad.qna.controller;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.service.QuestionService;
import com.codesquad.qna.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
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
    public String show(@PathVariable("id") long id, Model model) {
        Question question = questionService.findQuestionById(id);
        model.addAttribute("question", question);
        return "/qna/show";
    }
}
