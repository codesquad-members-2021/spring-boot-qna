package com.codessquad.qna.controller;

import com.codessquad.qna.model.Question;
import com.codessquad.qna.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.HttpSessionUtils.getUserFromSession;
import static com.codessquad.qna.utils.HttpSessionUtils.isLoginUser;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/form")
    public String qnaInputPage(HttpSession session) {
        isLoginUser(session);
        return "qna/questionInputForm";
    }

    @PostMapping("/form")
    public String newQuestion(Question question, HttpSession session) {
        questionService.save(question, getUserFromSession(session));
        return "redirect:/";
    }

    @GetMapping("/{questionId}")
    public String viewQuestion(@PathVariable Long questionId, Model model) {
        model.addAttribute("question", questionService.findById(questionId));
        return "qna/questionDetail";
    }

    @GetMapping("/{questionId}/updateForm")
    public String qnaUpdatePage(@PathVariable Long questionId, Model model, HttpSession session) {
        model.addAttribute("question", questionService.verifyQuestion(questionId, getUserFromSession(session)));
        return "qna/questionUpdateForm";
    }

    @PutMapping("/{questionId}/updateForm")
    public String updateQuestion(@PathVariable Long questionId, Question updatedQuestion, HttpSession session) {
        questionService.update(questionId, updatedQuestion, getUserFromSession(session));
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{questionId}")
    public String deleteQuestion(@PathVariable Long questionId, HttpSession session) {
        questionService.delete(questionId, getUserFromSession(session));
        return "redirect:/";
    }
}
