package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.question.Question;
import com.codessquad.qna.web.dto.question.QuestionRequest;
import com.codessquad.qna.web.service.QuestionService;
import com.codessquad.qna.web.utils.SessionUtils;
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

    @PostMapping
    public String create(HttpSession session, QuestionRequest request) {
        questionService.create(session, request);
        return "redirect:/";
    }

    @PutMapping("/{questionId}")
    public String update(@PathVariable long questionId, HttpSession session, QuestionRequest request) {
        questionService.update(questionId, session, request);
        return "redirect:/questions/" + questionId;
    }

    @GetMapping("/form")
    public String getForm(HttpSession session) {
        if (!SessionUtils.isLoginUser(session)) {
            return "/users/login-form";
        }
        return "qna/form";
    }

    @GetMapping("/{questionId}/form")
    public String getUpdateForm(@PathVariable long questionId, HttpSession session, Model model) {
        Question question = questionService.verifiedQuestion(questionId, session);
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @DeleteMapping("/{questionId}")
    public String delete(@PathVariable long questionId, HttpSession session) {
        questionService.delete(questionId, session);
        return "redirect:/";
    }

    @GetMapping("/{questionId}")
    public String show(@PathVariable long questionId, Model model) {
        model.addAttribute("question", questionService.getQuestionById(questionId));
        model.addAttribute("answers", questionService.findAllAnswer(questionId));
        return "/qna/show";
    }
}
