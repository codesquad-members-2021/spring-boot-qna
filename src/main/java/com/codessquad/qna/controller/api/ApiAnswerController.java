package com.codessquad.qna.controller.api;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.dto.AnswerDto;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    private final AnswerService answerService;

    public ApiAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public AnswerDto create(@PathVariable Long questionId, String contents, HttpSession session) {
        User user = HttpSessionUtils.getUserFromSession(session);
        return answerService.write(user, contents, questionId);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User user = HttpSessionUtils.getUserFromSession(session);
        answerService.delete(id, user);
        return "true";
    }

}
