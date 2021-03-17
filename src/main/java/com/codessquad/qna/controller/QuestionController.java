package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.exception.NotLoggedInException;
import com.codessquad.qna.service.QuestionService;
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
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoggedIn(session)) {
            throw new NotLoggedInException();
        }
        return "qna/form";
    }

    @PostMapping
    public String create(Question question, HttpSession session) {
        questionService.registerQuestion(question, HttpSessionUtils.getLoginUser(session));
        return "redirect:/";
    }

    @GetMapping("/{questionId}")
    public String show(@PathVariable("questionId") Long id, Model model) {
        model.addAttribute("question", questionService.getQuestionById(id));
        return "qna/show";
    }

    @GetMapping("/{questionId}/form")
    public String updateForm(@PathVariable("questionId") Long id, HttpSession session, Model model) {
        model.addAttribute("question",
                questionService.getQuestionWithAuthentication(id, HttpSessionUtils.getLoginUser(session)));
        return "/qna/updateForm";
    }

    @PutMapping("/{questionId}")
    public String update(@PathVariable("questionId") Long id, Question updatedQuestion, HttpSession session) {
        questionService.updateQuestion(id, HttpSessionUtils.getLoginUser(session), updatedQuestion);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        questionService.deleteQuestion(id, HttpSessionUtils.getLoginUser(session));
        return "redirect:/";
    }

}
