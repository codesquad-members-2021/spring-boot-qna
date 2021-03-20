package com.codessquad.qna.answer;

import com.codessquad.qna.question.Question;
import com.codessquad.qna.utils.SessionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    private final AnswerService answerService;

    public ApiAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public Answer create(@PathVariable Long questionId, Answer answer, HttpSession session) {
        answer.setQuestion(Question.builder().setId(questionId).build());
        answer.setWriter(SessionUtils.getSessionUser(session).toEntity());

        return answerService.create(answer);
    }
}
