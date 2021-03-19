package com.codessquad.qna.answer.ui;

import com.codessquad.qna.answer.application.AnswerService;
import com.codessquad.qna.answer.dto.AnswerRequest;
import com.codessquad.qna.question.application.QuestionService;
import com.codessquad.qna.user.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.codessquad.qna.common.HttpSessionUtils.checkAuthorization;
import static com.codessquad.qna.common.HttpSessionUtils.getUserAttribute;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    public AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @PostMapping
    public String createAnswer(@PathVariable Long questionId, @Valid AnswerRequest answerRequest, HttpSession session) {
        User writer = getUserAttribute(session);
        questionService.addAnswer(questionId, answerRequest, writer);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("{id}")
    public String deleteAnswer(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        checkAnswerAuthorization(id, session);
        answerService.deleteAnswer(id);
        return "redirect:/questions/" + questionId;
    }

    // FIXME: 권한 인증과 관련된 부분은 AOP 로 분리해야한다.
    private void checkAnswerAuthorization(Long id, HttpSession session) {
        Long writerId = answerService.getWriterId(id);
        checkAuthorization(writerId, session);
    }
}
