package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.User;
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
    public String createAnswer(@PathVariable("questionId") Long questionId, Answer answer, HttpSession session) {
        answerService.createAnswer(questionId, answer, HttpSessionUtils.getLoginUser(session));
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable("questionId") Long questionId,
                         @PathVariable("answerId") Long answerId,
                         HttpSession session) {
        User loginUser = HttpSessionUtils.getLoginUser(session);
        answerService.deleteAnswer(questionId, answerId, loginUser);
        return "redirect:/questions/" + questionId;
    }

    @GetMapping("/{answerId}/form")
    public String updateForm(@PathVariable("questionId") Long questionId,
                             @PathVariable("answerId") Long answerId,
                             HttpSession session, Model model) {
        User loginUser = HttpSessionUtils.getLoginUser(session);
        Answer answer = answerService.getAnswerWithAuthentication(questionId, answerId, loginUser);
        model.addAttribute("answer", answer);
        return "qna/updateAnswerForm";
    }

    @PutMapping("/{answerId}")
    public String update(@PathVariable("questionId") Long questionId,
                         @PathVariable("answerId") Long answerId,
                         Answer updatedAnswer,
                         HttpSession session) {
        User loginUser = HttpSessionUtils.getLoginUser(session);
        answerService.updateAnswer(questionId, answerId, loginUser, updatedAnswer);
        return "redirect:/questions/" + questionId;
    }

}
