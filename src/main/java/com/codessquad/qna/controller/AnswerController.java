package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.service.AnswerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public String create(@PathVariable("questionId") Long questionId, Answer answer, HttpSession session) {
        answerService.create(questionId, answer, HttpSessionUtils.loginUser(session));
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable("questionId") Long questionId,
                         @PathVariable("answerId") Long answerId,
                         HttpSession session) {
        answerService.delete(questionId, answerId, HttpSessionUtils.loginUser(session));
        return "redirect:/questions/" + questionId;
    }

    @GetMapping("/{answerId}/form")
    public String updateForm(@PathVariable("questionId") Long questionId,
                             @PathVariable("answerId") Long answerId,
                             HttpSession session, Model model) {
        Answer answer = answerService.answerWithAuthentication(questionId, answerId,
                HttpSessionUtils.loginUser(session));
        model.addAttribute("answer", answer);
        return "qna/updateAnswerForm";
    }

    @PutMapping("/{answerId}")
    public String update(@PathVariable("questionId") Long questionId,
                         @PathVariable("answerId") Long answerId,
                         Answer updatingAnswer,
                         HttpSession session) {
        answerService.update(questionId, answerId, HttpSessionUtils.loginUser(session), updatingAnswer);
        return "redirect:/questions/" + questionId;
    }

}
