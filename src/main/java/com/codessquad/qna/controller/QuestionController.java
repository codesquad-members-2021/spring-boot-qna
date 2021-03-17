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

    @GetMapping
    public String list(Model model) {
        model.addAttribute("questions", questionService.getList());
        return "qna/list";
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
        questionService.register(question, HttpSessionUtils.getLoginUser(session));
        return "redirect:/questions";
    }

    @GetMapping("/{questionId}")
    public String show(@PathVariable("questionId") Long id, Model model) {
        model.addAttribute("question", questionService.getById(id));
        return "qna/show";
    }

    @GetMapping("/{questionId}/form")
    public String updateForm(@PathVariable("questionId") Long id, HttpSession session, Model model) {
        model.addAttribute("question",
                questionService.getWithAuthentication(id, HttpSessionUtils.getLoginUser(session)));
        return "/qna/updateForm";
    }

    @PutMapping("/{questionId}")
    public String update(@PathVariable("questionId") Long id, Question updatingQuestion, HttpSession session) {
        questionService.update(id, HttpSessionUtils.getLoginUser(session), updatingQuestion);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        questionService.delete(id, HttpSessionUtils.getLoginUser(session));
        return "redirect:/questions";
    }

}
