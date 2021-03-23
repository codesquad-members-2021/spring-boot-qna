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

    @PutMapping("/{id}")
    public String update(@PathVariable long id, HttpSession session, QuestionRequest request) {
        questionService.update(id, session, request);
        return "redirect:/questions/" + id;
    }

    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        if (!SessionUtils.isLoginUser(session)) {
            return "/users/login-form";
        }
        return "qna/form";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, HttpSession session, Model model) {
        Question question = questionService.authenticate(id, session);
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, HttpSession session) {
        questionService.delete(id, session);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("question", questionService.getQuestionById(id));
        model.addAttribute("answers", questionService.list(id));
        return "/qna/show";
    }
}
