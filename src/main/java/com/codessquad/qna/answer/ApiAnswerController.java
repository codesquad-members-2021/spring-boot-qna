package com.codessquad.qna.answer;

import com.codessquad.qna.question.QuestionService;
import com.codessquad.qna.utils.SessionUtils;
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
    public AnswerDTO create(@PathVariable Long questionId, AnswerDTO answer, HttpSession session) {
        answer.setQuestionId(questionId);
        answer.setWriter(SessionUtils.getSessionUser(session));

        AnswerDTO result = answerService.create(answer);

        return result;
    }

    @DeleteMapping("/{id}")
    public AnswerDTO delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        AnswerDTO result = answerService.delete(id, SessionUtils.getSessionUser(session));

        return result;
    }
}
