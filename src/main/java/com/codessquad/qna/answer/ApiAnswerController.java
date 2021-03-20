package com.codessquad.qna.answer;

import com.codessquad.qna.question.Question;
import com.codessquad.qna.question.QuestionService;
import com.codessquad.qna.utils.SessionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    public ApiAnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @PostMapping
    public Answer create(@PathVariable Long questionId, Answer answer, HttpSession session) {
        answer.setQuestion(Question.builder().setId(questionId).build());
        answer.setWriter(SessionUtils.getSessionUser(session).toEntity());

        return answerService.create(answer);
    }

    @DeleteMapping("/{id}")
    public AnswerDTO delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        Answer result = answerService.delete(id, SessionUtils.getSessionUser(session));
        Question question = questionService.read(questionId);

        result.setQuestion(question);

        return AnswerDTO.from(result);
    }
}
