package com.codessquad.qna.controller.api;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.service.UserService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/questions/{questionId}/answers")
public class ApiAnswerController {

    private final AnswerService answerService;
    private final UserService userService;

    public ApiAnswerController(AnswerService answerService, UserService userService) {
        this.answerService = answerService;
        this.userService = userService;
    }

    @PostMapping
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        User user = HttpSessionUtils.getUserFromSession(session);
        //todo : DTO로 변환
        return answerService.write(user, contents, questionId);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User user = HttpSessionUtils.getUserFromSession(session);
        answerService.delete(id, user);
        return "redirect:/";
    }

}
