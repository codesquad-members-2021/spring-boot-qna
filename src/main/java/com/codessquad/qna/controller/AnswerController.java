package com.codessquad.qna.controller;

import com.codessquad.qna.model.Answer;
import com.codessquad.qna.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.HttpSessionUtils.getUserFromSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping
    public String createAnswer(@PathVariable Long questionId, Answer answer, HttpSession session) {
        answerService.save(questionId, answer, getUserFromSession(session));
        return "redirect:/questions/" + questionId;
    }

    @GetMapping("/{answerId}/updateForm")
    public String answerUpdatePage(@PathVariable Long answerId, Model model, HttpSession session) {
        model.addAttribute("answers", answerService.getAnswer(answerId, getUserFromSession(session)));
        return "qna/answerUpdateForm";
    }

    @PutMapping("/{answerId}/updateForm")
    public String updateAnswer(@PathVariable Long questionId, @PathVariable Long answerId, Answer updatedAnswer, HttpSession session) {
        answerService.update(answerId, updatedAnswer, getUserFromSession(session));
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{answerId}")
    public String deleteAnswer(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        answerService.delete(answerId, getUserFromSession(session));
        return "redirect:/questions/" + questionId;
    }
}
