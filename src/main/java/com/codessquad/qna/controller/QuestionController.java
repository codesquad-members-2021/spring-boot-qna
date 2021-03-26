package com.codessquad.qna.controller;

import com.codessquad.qna.model.Question;
import com.codessquad.qna.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.HttpSessionUtils.getUserFromSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/form")
    public String qnaInputPage(HttpSession session) {
        getUserFromSession(session);
        return "qna/questionInputForm";
    }

    @PostMapping("/form")
    public String newQuestion(Question question, HttpSession session) {
        questionService.save(question, session);
        return "redirect:/";
    }

    @GetMapping("/{questionId}")
    public String viewQuestion(@PathVariable Long questionId, Model model) {
        model.addAttribute("question", questionService.findById(questionId));
        return "qna/questionDetail";
    }

    @GetMapping("/{questionId}/updateForm")
    public String qnaUpdatePage(@PathVariable Long questionId, Model model, HttpSession session) {
        Question question = questionService.findById(questionId);
        questionService.verifyWriter(getUserFromSession(session), question);
        model.addAttribute("question", question);
        return "qna/questionUpdateForm";
    }

    @PutMapping("/{questionId}/updateForm")
    public String updateQuestion(@PathVariable Long questionId, Question updatedQuestion, HttpSession session) {
        Question question = questionService.findById(questionId);
        questionService.verifyWriter(getUserFromSession(session), question);
        questionService.update(question, updatedQuestion, session);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{questionId}")
    public String deleteQuestion(@PathVariable Long questionId, HttpSession session) {
        Question question = questionService.findById(questionId);
        questionService.verifyWriter(getUserFromSession(session), question);
        questionService.delete(question);
        return "redirect:/";
    }
}
