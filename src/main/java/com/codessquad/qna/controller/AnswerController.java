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
        answerService.createAnswer(questionId, answer, HttpSessionUtils.getLoginUser(session));
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable("questionId") Long questionId,
                         @PathVariable("answerId") Long answerId,
                         HttpSession session) {
        answerService.deleteAnswer(questionId, answerId, HttpSessionUtils.getLoginUser(session));
        return "redirect:/questions/" + questionId;
    }

    @GetMapping("/{answerId}/form")
    public String updateForm(@PathVariable("questionId") Long questionId,
                             @PathVariable("answerId") Long answerId,
                             HttpSession session, Model model) {
        Answer answer = answerService.getAnswerWithAuthentication(questionId, answerId,
                HttpSessionUtils.getLoginUser(session));
        model.addAttribute("answer", answer);
        return "qna/updateAnswerForm";
    }

    @PutMapping("/{answerId}")
    public String update(@PathVariable("questionId") Long questionId,
                         @PathVariable("answerId") Long answerId,
                         Answer updatedAnswer,
                         HttpSession session) {
        answerService.updateAnswer(questionId, answerId, HttpSessionUtils.getLoginUser(session), updatedAnswer);
        return "redirect:/questions/" + questionId;
    }

}
