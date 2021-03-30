package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.HttpSessionUtils.getUserFromSession;
import static com.codessquad.qna.HttpSessionUtils.isLoginUser;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!isLoginUser(session)) {
            return "redirect:/users/login";
        }
        return "qna/form";
    }

    @PostMapping
    public String createQuestion(Question question, HttpSession session) {
        if (!isLoginUser(session)) {
            return "redirect:/users/login";
        }
        questionService.save(question, getUserFromSession(session));
        return "redirect:/";
    }

    @GetMapping
    public String getQuestionList(Model model) {
        model.addAttribute("questions", questionService.listAllQuestions());
        return "index";
    }

    @GetMapping("/{id}")
    public String getDetailedQuestion(@PathVariable Long id, Model model) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!isLoginUser(session)) { return "redirect:/users/login"; }

        Question question = validateWriter(id, session);
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String modifyQuestion(@PathVariable Long id, HttpSession session, Question modifiedQuestion) {
        if (!isLoginUser(session)) { return "redirect:/users/login"; }

        Question originalQuestion = validateWriter(id, session);
        questionService.modifyQuestion(originalQuestion, modifiedQuestion);
        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        if (!isLoginUser(session)) { return "redirect:/users/login"; }

        Question question = validateWriter(id, session);
        questionService.deleteQuestion(question);
        return "redirect:/";
    }

    private Question validateWriter(Long id, HttpSession session) {
        Question question = questionService.findById(id);
        User user = getUserFromSession(session);
        if (!question.isSameUser(user)) {
            throw new IllegalUserAccessException();
        }
        return question;
    }
}
