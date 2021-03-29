package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.AnswerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.HttpSessionUtils.getUserFromSession;
import static com.codessquad.qna.HttpSessionUtils.isLoginUser;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!isLoginUser(session)) {
            return "redirect:/users/login";
        }

        User writer = getUserFromSession(session);
        answerService.save(writer, contents, questionId);
        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if (!isLoginUser(session)) {
            return "redirect:/users/login";
        }

        User loginUser = getUserFromSession(session);
        answerService.deleteById(id, loginUser);

        return "redirect:/questions/{questionId}";
    }
}
