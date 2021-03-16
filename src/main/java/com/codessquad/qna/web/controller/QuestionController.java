package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.HttpSessionUtils;
import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.service.QuestionService;
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
    public String getQuestionForm(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        return "/qna/form";
    }

    @PostMapping
    public String createQuestion(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        questionService.postQuestion(question, HttpSessionUtils.getSessionedUser(session));
        return "redirect:/questions";
    }

    @GetMapping
    public String getQuestions(Model model) {
        model.addAttribute("questions", questionService.findQuestions());
        return "/index";
}

    @GetMapping("/{id}")
    public String getQuestion(@PathVariable long id, Model model) {
        try {
            model.addAttribute("question", questionService.findQuestion(id));
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable long id, HttpSession session, Model model) {
        System.out.println("야호");
        if(!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        Question originQuestion = questionService.findQuestion(id);
        User sessionedUser = HttpSessionUtils.getSessionedUser(session);
        if(!originQuestion.getWriter().equals(sessionedUser.getUserId())) {
            throw new IllegalStateException("자신의 글만 수정할 수 있습니다");
        }

        model.addAttribute("question", originQuestion);
        return "/qna/updateForm";
    }

    @PutMapping ("/{id}")
    public String updateQuestion(@PathVariable long id, Question question, HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        Question originQuestion = questionService.findQuestion(id);
        User sessionedUser = HttpSessionUtils.getSessionedUser(session);
        if(!originQuestion.getWriter().equals(sessionedUser.getUserId())) {
            throw new IllegalStateException("자신의 글만 수정할 수 있습니다");
        }

        questionService.updateQuestion(originQuestion, question);
        return "redirect:/questions/";
    }
}
