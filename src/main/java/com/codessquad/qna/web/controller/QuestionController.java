package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.HttpSessionUtils;
import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exception.NotLoginException;
import com.codessquad.qna.web.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/form")
    public String getQuestionForm(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new NotLoginException();
        }
        return "/qna/form";
    }

    @PostMapping
    public String createQuestion(Question question, HttpSession session) {
        User user = HttpSessionUtils.getSessionedUser(session).orElseThrow(NotLoginException::new);
        questionService.postQuestion(question, user);
        return "redirect:/questions";
    }

    @GetMapping
    public String getQuestions(Model model) {
        model.addAttribute("questions", questionService.findQuestions());
        return "/index";
    }

    @GetMapping("/{id}")
    public String getQuestion(@PathVariable long id, Model model) {
        model.addAttribute("question", questionService.findQuestion(id));
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable long id, HttpSession session, Model model) {
        User user = HttpSessionUtils.getSessionedUser(session).orElseThrow(NotLoginException::new);
        Question question = questionService.checkAndGetQuestion(id, user);
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable long id, Question question, HttpSession session) {
        User user = HttpSessionUtils.getSessionedUser(session).orElseThrow(NotLoginException::new);
        questionService.updateQuestion(id, user, question);
        return "redirect:/questions/";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable long id, HttpSession session) {
        User user = HttpSessionUtils.getSessionedUser(session).orElseThrow(NotLoginException::new);
        questionService.deleteQuestion(id, user);
        return "redirect:/questions";
    }
}
