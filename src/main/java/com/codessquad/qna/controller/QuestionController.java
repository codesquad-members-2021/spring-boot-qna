package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
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
    public String questionForm(HttpSession session) {
        if (!HttpSessionUtils.isLoggedIn(session)) {
            throw new NotLoggedInException();
        }
        return "qna/form";
    }

    @PostMapping()
    public String query(Question question, HttpSession session) {
        questionService.registerQuestion(question, HttpSessionUtils.getLoginUser(session));
        return "redirect:/";
    }

    @GetMapping("/{questionId}")
    public String qnaShow(@PathVariable("questionId") Long id, Model model) {
        model.addAttribute("question", questionService.getQuestionById(id));
        return "qna/show";
    }

    @GetMapping("/{questionId}/form")
    public String updateForm(@PathVariable("questionId") Long id, HttpSession session, Model model) {
        User loginUser = HttpSessionUtils.getLoginUser(session);
        model.addAttribute("question", questionService.getQuestionWithAuthentication(id, loginUser));
        return "/qna/updateForm";
    }

    @PutMapping("/{questionId}")
    public String update(@PathVariable("questionId") Long id, Question updatedQuestion, HttpSession session) {
        User loginUser = HttpSessionUtils.getLoginUser(session);
        questionService.updateQuestion(id, loginUser, updatedQuestion);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        User loginUser = HttpSessionUtils.getLoginUser(session);
        questionService.deleteQuestion(id, loginUser);
        return "redirect:/";
    }

}
